package es.library.databaseserver.contenido.crud.service.implementations;

import static es.library.databaseserver.contenido.crud.model.ContenidoModelSetDTOMapper.ContenidoModelSetToContenido;
import static es.library.databaseserver.contenido.crud.model.ContenidoModelSetDTOMapper.ContenidoToContenidoModelSet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.crud.dao.ContenidoDAO;
import es.library.databaseserver.contenido.crud.dao.ContenidoDetallesAudiovisualDAO;
import es.library.databaseserver.contenido.crud.dao.ContenidoDetallesLibroDAO;
import es.library.databaseserver.contenido.crud.model.ContenidoModelSet;
import es.library.databaseserver.contenido.crud.model.ContenidoValidator;
import es.library.databaseserver.contenido.crud.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.crud.model.DetallesLibroModel;
import es.library.databaseserver.contenido.crud.service.ContenidoService;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.IllegalContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;

@Service
public class ContenidoServiceImpl implements ContenidoService{

	@Autowired
	private ContenidoDAO contenidoRepository;
	
	@Autowired
	private ContenidoDetallesAudiovisualDAO dAudiovisualDAO;
	
	@Autowired
	private ContenidoDetallesLibroDAO dLibroDAO;
	
	@Autowired
	private ContenidoValidator validator;
	
	public List<Contenido> getAllContenidos(){	
		return idListToContenidoList(contenidoRepository.getAllContenidosID());
	}
	
	public Contenido getContenidoByID(long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException {
		var cSet = getContenidoModelSetByID(ID);
		
		return ContenidoModelSetToContenido(cSet);
	}
	
	public Contenido insertContenido(Contenido contenido) throws DatabaseContenidoException, NotValidTypeContenidoException, NotValidSoporteException, ContenidoAlreadyExistsException{
		//Compruebo si el contenido tiene algun error
		validator.validateContenidoCorrect(contenido);
		
		var cSet = ContenidoToContenidoModelSet(contenido);
		Optional<DetallesAudiovisualModel> audiovisualModel = Optional.empty();
		Optional<DetallesLibroModel> libroModel = Optional.empty();
		
		if(contenido instanceof Audio) {
			audiovisualModel = this.insertAudio(cSet);
			cSet.getContenido().setIDAudiovisual(audiovisualModel.get().getID());
		}
		else if(contenido instanceof Libro) {
			libroModel = this.insertLibro(cSet);
			cSet.getContenido().setIDLibro(libroModel.get().getID());
		}
		else {
			throw new NotValidTypeContenidoException("Su libro no tiene ningún tipo");
		}
		
		var c = contenidoRepository.insertContenido(cSet.getContenido());
		
		if(audiovisualModel.isEmpty() && libroModel.isEmpty()) throw new NotValidTypeContenidoException("Un contenido debe ser un libro o un audiovisual");
		
		return ContenidoModelSetToContenido(new ContenidoModelSet(c, audiovisualModel.orElse(null), libroModel.orElse(null)));
	}
	
	public void deleteContenidoByID(long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException {
		var a = getContenidoByID(ID);
		contenidoRepository.deleteContenidoByID(ID);
		if(a instanceof Audio)dAudiovisualDAO.deleteAudiovisualByIDIfIsNotPointed(a.getIDAudiovisual(),false);
		if(a instanceof Libro)dLibroDAO.deleteLibroByIDIfIsNotPointed(a.getIDLibro(),false);
	}
	
	public Contenido updateContenidoByID(long ID, Contenido contenido) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException, DatabaseContenidoException, ContenidoAlreadyExistsException,IllegalContenidoException {
		validator.validateContenidoCorrectUpdating(contenido);
		
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
			cSetNew.getContenido().setIDAudiovisual(dAudiovisual.get().getID());
		}
		
		//Si el libro no es nulo
		if (cSetNew.getLibro() != null) {
			dLibro = insertLibro(cSetNew);
			cSetNew.getContenido().setIDLibro(dLibro.get().getID());
		}
		
		
		//Si el contenido antiguo no es igual al nuevo, lo actualizo
		if(!(cSetOld.getContenido().equals(cSetNew.getContenido()))) {
			c = contenidoRepository.updateContenidoByID(ID, cSetNew.getContenido());
			if (cSetNew.getLibro() != null) dLibroDAO.deleteLibroByIDIfIsNotPointed(cSetOld.getContenido().getIDLibro(),false);
			if(cSetNew.getAudiovisual() != null) dAudiovisualDAO.deleteAudiovisualByIDIfIsNotPointed(cSetOld.getContenido().getIDAudiovisual(),false);
		}
		else {
			c = cSetNew.getContenido();
		}
		
		return ContenidoModelSetToContenido(new ContenidoModelSet(c, dAudiovisual.orElse(null), dLibro.orElse(null)));
	}
	
	private ContenidoModelSet getContenidoModelSetByID(long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException {
		Contenido c = contenidoRepository.getContenidoByID(ID).orElseThrow(
				() -> new ContenidoNotFoundException("El contenido que se pide no existe con id: "+ID));
		var audio = dAudiovisualDAO.getAudiovisualByID(c.getIDAudiovisual());
		var libro = dLibroDAO.getLibroByID(c.getIDLibro());
		
		if(audio.isEmpty() && libro.isEmpty()) {
			throw new NotValidTypeContenidoException("Un contenido no puede ser ni libro ni audiovisual");
		}
		
		return new ContenidoModelSet(c, audio.orElse(null),libro.orElse(null));
	}
	
	private Optional<DetallesAudiovisualModel> insertAudio(ContenidoModelSet cSet) throws DatabaseContenidoException, ContenidoAlreadyExistsException {
		Optional<DetallesAudiovisualModel> a = dAudiovisualDAO.getAudiovisualIfIdIsNull(cSet.getAudiovisual());
		
		if(a.isPresent()) {
			return a;
		}
		
		//Si el audiovisual no existia en la tabla,lo creo
		return Optional.ofNullable(dAudiovisualDAO.insertAudiovisual(cSet.getAudiovisual()));
	}
	
	private Optional<DetallesLibroModel> insertLibro(ContenidoModelSet cSet) throws DatabaseContenidoException{
		Optional<DetallesLibroModel> l = dLibroDAO.getLibroIfIdIsNull(cSet.getLibro());
		
		if(l.isPresent()) {
			return l;
		}
		
		//Si el libro no existia en la tabla,lo creo
		return Optional.ofNullable(dLibroDAO.insertLibro(cSet.getLibro()));
	}
	
	public List<Contenido> idListToContenidoList(List<Long> ids) {
		return ids.stream().map(id -> getContenidoByID(id)).toList();
	}
}
