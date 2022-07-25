package es.library.databaseserver.contenido.service.implementations;

import static es.library.databaseserver.contenido.model.ContenidoModelSetDTOMapper.ContenidoModelSetToContenido;
import static es.library.databaseserver.contenido.model.ContenidoModelSetDTOMapper.ContenidoToContenidoModelSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.dao.ContenidoDAO;
import es.library.databaseserver.contenido.dao.ContenidoDetallesAudiovisualDAO;
import es.library.databaseserver.contenido.dao.ContenidoDetallesLibroDAO;
import es.library.databaseserver.contenido.dto.Audio;
import es.library.databaseserver.contenido.dto.Libros;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModelSet;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;
import es.library.databaseserver.contenido.service.ContenidoService;
import es.library.databaseserver.contenido.service.DetallesAudiovisualService;
import es.library.databaseserver.contenido.service.DetallesLibroService;;

@Service
public class ContenidoServiceImpl implements ContenidoService{

	@Autowired
	private ContenidoDAO contenidoRepository;
	
	@Autowired
	private ContenidoDetallesAudiovisualDAO dAudiovisualDAO;
	
	@Autowired
	private ContenidoDetallesLibroDAO dLibroDAO;
	
	public List<Contenido> getAllContenidos(){
		List<Contenido> resultadoList = new ArrayList<>();
		
		for(long id : contenidoRepository.getAllContenidosID()) {
			try {
				resultadoList.add(getContenidoByID(id));
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		
		return resultadoList;
	}
	
	public Contenido getContenidoByID(long ID) throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException {
		var cSet = getContenidoModelSetByID(ID);
		
		return ContenidoModelSetToContenido(cSet);
	}
	
	//TODO
	public Contenido insertContenido(Contenido contenido) throws NotInsertedContenidoException, NotValidTypeContenidoException, NotValidSoporteException, ContenidoAlreadyExistsException{
		var cSet = ContenidoToContenidoModelSet(contenido);
		Optional<DetallesAudiovisualModel> audiovisualModel = null;
		Optional<DetallesLibroModel> libroModel = null;
		
		if(contenido instanceof Audio) {
			audiovisualModel = this.insertAudio(cSet);
		}
		else if(contenido instanceof Libros) {
			libroModel = this.insertLibro(cSet);
		}
		else {
			throw new NotValidTypeContenidoException("Su libro no tiene ningún tipo");
		}
		
		var c = contenidoRepository.insertContenido(cSet.getContenido());
		
		if(audiovisualModel.isEmpty() && libroModel.isEmpty()) throw new NotValidTypeContenidoException("Un contenido debe ser un libro o un audiovisual");
		
		return ContenidoModelSetToContenido(new ContenidoModelSet(c, audiovisualModel.get(), libroModel.get()));
	}
	
	public Contenido deleteContenidoByID(long ID) throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException {
		var cSet = getContenidoModelSetByID(ID);
		
		if(cSet.getAudiovisual() != null) {
			dAudiovisualService.deleteAudiovisualByID(cSet.getAudiovisual().getID());
		}	
		
		if(cSet.getLibro() != null) {
			dLibroService.deleteLibroByID(cSet.getLibro().getID());
		}
		
		return contenidoRepository.deleteContenidoByID(ID);
	}
	
	//TODO No actualiza en las tablas de detalles
	public Contenido updateContenidoByID(long ID, Contenido contenido) throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException, NotInsertedContenidoException {
		ContenidoModelSet cSetTo = ContenidoToContenidoModelSet(contenido);
		ContenidoModelSet cSetFrom = getContenidoModelSetByID(ID);
		
		if(cSetFrom.equals(cSetTo)) return;
		
		if(!(cSetFrom.getContenido().equals(cSetTo.getContenido()))) {
			contenidoRepository.updateContenidoByID(ID, cSetTo.getContenido());
		}
		
		if(cSetTo.getAudiovisual() != null && cSetFrom.getAudiovisual() != null) {
			if(!(cSetFrom.getAudiovisual().equals(cSetTo.getAudiovisual()))) {
				updateAudiovisualByID(
						cSetFrom.getContenido().getIDAudiovisual(),
						cSetTo.getAudiovisual());
			}
		}
		else if (cSetTo.getLibro() != null && cSetFrom.getLibro() != null) {
			if(!(cSetFrom.getLibro().equals(cSetTo.getLibro()))) {
				updateLibroByID(
						cSetFrom.getContenido().getIDLibro(), 
						cSetTo.getLibro());
			}
		}
	}
	
	private ContenidoModelSet getContenidoModelSetByID(long ID) throws NoSuchContenidoException, NotValidTypeContenidoException {
		Contenido c = contenidoRepository.getContenidoByID(ID).orElseThrow(
				() -> new NoSuchContenidoException("El contenido que se pide no existe"));
		var audio = dAudiovisualDAO.getAudiovisualByID(c.getIDAudiovisual());
		var libro = dLibroDAO.getLibroByID(c.getIDLibro());
		
		if(audio.isEmpty() && libro.isEmpty()) {
			throw new NotValidTypeContenidoException("Un contenido no puede ser ni libro ni audiovisual");
		}
		
		return new ContenidoModelSet(c, audio.get(),libro.get());
	}
	
	private Optional<DetallesAudiovisualModel> insertAudio(ContenidoModelSet cSet) {
		Optional<DetallesAudiovisualModel> a = dAudiovisualDAO.getAudiovisualByID(cSet.getContenido().getIDAudiovisual());
		
		//Si los detalles audiovisual no estan definidos se deja que se coja el que ya esta hecho
		if(cSet.getAudiovisual().getCalidad() <= 0 &&
				!cSet.getAudiovisual().getIsVideo() &&
				cSet.getAudiovisual().getDuracion() <= 0&&
				cSet.getAudiovisual().getEdadRecomendada() <= 0) {
			return Optional.empty();
		}
		if(!cSet.getAudiovisual().equals(a.get()))
			throw new NotValidTypeContenidoException("No se puede añadir este contenido audiovisual a la base de datos por que existe otro con la misma ID");
		
		return Optional.ofNullable(dAudiovisualDAO.insertAudiovisual(cSet.getAudiovisual()));
	}
	
	private Optional<DetallesLibroModel> insertLibro(ContenidoModelSet cSet) {
		Optional<DetallesLibroModel> l = dLibroDAO.getLibroByID(cSet.getContenido().getIDLibro());
		
		//Si los detalles libro no estan definidos se deja que se coja el que ya esta hecho
		if(cSet.getLibro().getEditorial() == null && 
				cSet.getLibro().getISBN() == null && 
				cSet.getLibro().getPaginas() <= 0) {
			return Optional.empty();
		}
		if(!cSet.getLibro().equals(l.get()))
			throw new NotValidTypeContenidoException("No se puede añadir este libro a la base de datos por que existe otro con la misma ID y es distinto");
		
		return Optional.ofNullable(dLibroDAO.insertLibro(cSet.getLibro()));
	}
	
}
