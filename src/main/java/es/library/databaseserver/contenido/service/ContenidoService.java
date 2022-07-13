package es.library.databaseserver.contenido.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.dao.ContenidoDAO;
import es.library.databaseserver.contenido.exceptions.ContenidoNotInsertedException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModel;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;
import static es.library.databaseserver.contenido.service.ContenidoModelSetDTOMapper.ContenidoDTOToContenidoModelSet;
import static es.library.databaseserver.contenido.service.ContenidoModelSetDTOMapper.ContenidoModelSetToContenidoDTO;;

@Service
public class ContenidoService {

	private final ContenidoDAO contenidoRepository;
	
	@Autowired
	public ContenidoService(@Qualifier("contenidoSQLite") ContenidoDAO contenidoRepository) {
		this.contenidoRepository = contenidoRepository;
	}
	
	public List<ContenidoModel> getAllContenidos(){
		return contenidoRepository.getAllContenidos();
	}
	
	public ContenidoModel getContenidoByID(Long ID) throws NoSuchContenidoException {
		return contenidoRepository.getContenidoByID(ID);
	}
	
	public void insertContenido(ContenidoModel contenido) throws ContenidoNotInsertedException{
		contenidoRepository.insertContenido(contenido);
	}
	
	public void deleteContenidoByID(Long ID)  throws  NoSuchContenidoException {
		contenidoRepository.deleteContenidoByID(ID);
	}
	
	public void updateContenidoByID(Long ID, ContenidoModel contenido) throws NoSuchContenidoException {
		contenidoRepository.updateContenidoByID(ID, contenido);
	}
	
	//DETALLES AUDIOVISUAL
	public List<DetallesAudiovisualModel> getAllAudiovisual(){return contenidoRepository.getAllAudiovisual();}
		
	public DetallesAudiovisualModel getAudiovisualByID(Long ID) throws NoSuchContenidoException{return contenidoRepository.getAudiovisualByID(ID);}
		
	public void insertAudiovisual(DetallesAudiovisualModel audiovisual) throws ContenidoNotInsertedException{contenidoRepository.insertAudiovisual(audiovisual);}
		
	public void deleteAudiovisualByID(Long ID)  throws NoSuchContenidoException{contenidoRepository.deleteAudiovisualByID(ID);}
		
	public void updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual)  throws NoSuchContenidoException{contenidoRepository.updateAudiovisualByID(ID, audiovisual);}

	//DETALLES LIBROS
	public List<DetallesLibroModel> getAllLibro(){return contenidoRepository.getAllLibro();}
		
	public DetallesLibroModel getLibroByID(Long ID) throws NoSuchContenidoException{return contenidoRepository.getLibroByID(ID);}
		
	public void insertLibro(DetallesLibroModel libro) throws ContenidoNotInsertedException{contenidoRepository.insertLibro(libro);}
			
	public void deleteLibroByID(Long ID) throws NoSuchContenidoException{contenidoRepository.deleteLibroByID(ID);}
			
	public void updateLibroByID(Long ID, DetallesLibroModel libro)  throws NoSuchContenidoException{contenidoRepository.updateLibroByID(ID, libro);}
	
}
