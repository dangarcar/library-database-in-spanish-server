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
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.model.ContenidoModelSet;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;
import es.library.databaseserver.contenido.service.ContenidoService;

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
				System.err.println(e.getClass().getName());
			}
		}
		
		return resultadoList;
	}
	
	public Contenido getContenidoByID(long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException {
		var cSet = getContenidoModelSetByID(ID);
		
		return ContenidoModelSetToContenido(cSet);
	}
	
	public Contenido insertContenido(Contenido contenido) throws DatabaseContenidoException, NotValidTypeContenidoException, NotValidSoporteException, ContenidoAlreadyExistsException{
		var cSet = ContenidoToContenidoModelSet(contenido);
		Optional<DetallesAudiovisualModel> audiovisualModel = Optional.empty();
		Optional<DetallesLibroModel> libroModel = Optional.empty();
		
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
		
		return ContenidoModelSetToContenido(new ContenidoModelSet(c, audiovisualModel.orElse(null), libroModel.orElse(null)));
	}
	
	public void deleteContenidoByID(long ID) throws ContenidoNotFoundException {
		contenidoRepository.deleteContenidoByID(ID);
	}
	
	public Contenido updateContenidoByID(long ID, Contenido contenido) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException, DatabaseContenidoException, ContenidoAlreadyExistsException {
		ContenidoModelSet cSetNew = ContenidoToContenidoModelSet(contenido);
		ContenidoModelSet cSetOld = getContenidoModelSetByID(ID);
		
		Contenido c = null;
		Optional<DetallesLibroModel> dLibro = Optional.empty();
		Optional<DetallesAudiovisualModel> dAudiovisual = Optional.empty();
		
		//Si cSet viejo y el nuevo son iguales, devuelve el nuevo
		if(cSetOld.equals(cSetNew)) {
			return ContenidoModelSetToContenido(cSetNew);
		}
		
		//Si el audiovisual no es nulo
		if(cSetNew.getAudiovisual() != null) {
			dAudiovisual = insertAudio(cSetNew);
		}
		
		//Si el libro no es nulo
		if (cSetNew.getLibro() != null) {
			dLibro = insertLibro(cSetNew);
		}
		
		
		//Si el contenido antiguo no es igual al nuevo, lo actualizo
		if(!(cSetOld.getContenido().equals(cSetNew.getContenido()))) {
			c = contenidoRepository.updateContenidoByID(ID, cSetNew.getContenido());
		}
		else {
			c = cSetNew.getContenido();
		}
		
		return ContenidoModelSetToContenido(new ContenidoModelSet(c, dAudiovisual.orElse(null), dLibro.orElse(null)));
	}
	
	private ContenidoModelSet getContenidoModelSetByID(long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException {
		Contenido c = contenidoRepository.getContenidoByID(ID).orElseThrow(
				() -> new ContenidoNotFoundException("El contenido que se pide no existe"));
		var audio = dAudiovisualDAO.getAudiovisualByID(c.getIDAudiovisual());
		var libro = dLibroDAO.getLibroByID(c.getIDLibro());
		
		if(audio.isEmpty() && libro.isEmpty()) {
			throw new NotValidTypeContenidoException("Un contenido no puede ser ni libro ni audiovisual");
		}
		
		return new ContenidoModelSet(c, audio.orElse(null),libro.orElse(null));
	}
	
	private Optional<DetallesAudiovisualModel> insertAudio(ContenidoModelSet cSet) throws DatabaseContenidoException, ContenidoAlreadyExistsException {
		Optional<DetallesAudiovisualModel> a = dAudiovisualDAO.getAudiovisualByID(cSet.getContenido().getIDAudiovisual());
		
		if(a.isPresent()) {
			//Si los detalles audiovisual no estan definidos se deja que se coja el que ya esta hecho
			if(cSet.getAudiovisual().getCalidad() <= 0 &&
					!cSet.getAudiovisual().getIsVideo() &&
					cSet.getAudiovisual().getDuracion() <= 0&&
					cSet.getAudiovisual().getEdadRecomendada() <= 0) {
				return a;
			}
			if(!cSet.getAudiovisual().equals(a.get()))
				throw new ContenidoAlreadyExistsException("No se puede añadir este contenido audiovisual a la base de datos por que existe otro con la misma ID");
			else {
				return a;
			}
		}
		
		//Si el audiovisual no existia en la tabla,lo creo
		return Optional.ofNullable(dAudiovisualDAO.insertAudiovisual(cSet.getAudiovisual()));
	}
	
	private Optional<DetallesLibroModel> insertLibro(ContenidoModelSet cSet) throws DatabaseContenidoException, ContenidoAlreadyExistsException {
		Optional<DetallesLibroModel> l = dLibroDAO.getLibroByID(cSet.getContenido().getIDLibro());
		
		if(l.isPresent()) {
			//Si los detalles libro no estan definidos se deja que se coja el que ya esta hecho
			if(cSet.getLibro().getEditorial() == null && 
					cSet.getLibro().getISBN() == null && 
					cSet.getLibro().getPaginas() <= 0) {
				return l;
			}
			if(!cSet.getLibro().equals(l.get())) {
				throw new ContenidoAlreadyExistsException("No se puede añadir este libro a la base de datos por que existe otro con la misma ID y es distinto");
			}
			else {
				return l;
			}
		}
		
		//Si el libro no existia en la tabla,lo creo
		return Optional.ofNullable(dLibroDAO.insertLibro(cSet.getLibro()));
	}
	
}
