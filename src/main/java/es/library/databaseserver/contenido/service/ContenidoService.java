package es.library.databaseserver.contenido.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.dao.ContenidoDAO;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModelSet;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;
import static es.library.databaseserver.contenido.service.ContenidoModelSetDTOMapper.ContenidoModelSetToContenido;

@Service
public class ContenidoService {

	private final ContenidoDAO contenidoRepository;
	
	@Autowired
	public ContenidoService(@Qualifier("contenidoSQLite") ContenidoDAO contenidoRepository) {
		this.contenidoRepository = contenidoRepository;
	}
	
	//TODO Error handling
	public List<Contenido> getAllContenidos(){
		List<Contenido> resultadoList = new ArrayList<>();
		
		List<Contenido> listC= contenidoRepository.getAllContenidos();
		
		for(var c:listC) {
			try {
				DetallesAudiovisualModel audio = null;
				DetallesLibroModel libro = null;
				try {
					audio = contenidoRepository.getAudiovisualByID(c.getIDAudiovisual());
				} catch (NoSuchContenidoException e) {
					System.err.println(e.getMessage()+" Audio");
				}
				
				try {
					libro = contenidoRepository.getLibroByID(c.getIDLibro());
				} catch (NoSuchContenidoException e) {
					System.err.println(e.getMessage()+" Libro");
				}
				
				var cSet = new ContenidoModelSet(c, audio,libro);
				
				Contenido contenido = ContenidoModelSetToContenido(cSet);
				
				System.out.println(contenido.toString());
				
				resultadoList.add(contenido);
			} catch(NotValidSoporteException|NotValidTypeContenidoException e) {
				System.err.println(e.getMessage());
			}
		}
		
		
		return resultadoList;
	}
	
	public Contenido getContenidoByID(Long ID) throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException {
		var c = contenidoRepository.getContenidoByID(ID);
		DetallesAudiovisualModel audio = null;
		DetallesLibroModel libro = null;

		try {
			audio = contenidoRepository.getAudiovisualByID(c.getIDAudiovisual());
		} catch (NoSuchContenidoException e) {
			//Esta excepcion es normal
		}
		try {
			libro = contenidoRepository.getLibroByID(c.getIDLibro());
		} catch (NoSuchContenidoException e) {
			//Esta excepcion es normal
		}
		
		var cSet = new ContenidoModelSet(c, audio,libro);
		
		Contenido contenido = ContenidoModelSetToContenido(cSet);
		
		return contenido;
	}
	
	public void insertContenido(Contenido contenido) throws NotInsertedContenidoException{
		contenidoRepository.insertContenido(contenido);
	}
	
	public void deleteContenidoByID(Long ID)  throws  NoSuchContenidoException {
		contenidoRepository.deleteContenidoByID(ID);
	}
	
	public void updateContenidoByID(Long ID, Contenido contenido) throws NoSuchContenidoException {
		contenidoRepository.updateContenidoByID(ID, contenido);
	}
	
	//DETALLES AUDIOVISUAL
	public List<DetallesAudiovisualModel> getAllAudiovisual(){return contenidoRepository.getAllAudiovisual();}
		
	public DetallesAudiovisualModel getAudiovisualByID(Long ID) throws NoSuchContenidoException{return contenidoRepository.getAudiovisualByID(ID);}
		
	public void insertAudiovisual(DetallesAudiovisualModel audiovisual) throws NotInsertedContenidoException{contenidoRepository.insertAudiovisual(audiovisual);}
		
	public void deleteAudiovisualByID(Long ID)  throws NoSuchContenidoException{contenidoRepository.deleteAudiovisualByID(ID);}
		
	public void updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual)  throws NoSuchContenidoException{contenidoRepository.updateAudiovisualByID(ID, audiovisual);}

	//DETALLES LIBROS
	public List<DetallesLibroModel> getAllLibro(){return contenidoRepository.getAllLibro();}
		
	public DetallesLibroModel getLibroByID(Long ID) throws NoSuchContenidoException{return contenidoRepository.getLibroByID(ID);}
		
	public void insertLibro(DetallesLibroModel libro) throws NotInsertedContenidoException{contenidoRepository.insertLibro(libro);}
			
	public void deleteLibroByID(Long ID) throws NoSuchContenidoException{contenidoRepository.deleteLibroByID(ID);}
			
	public void updateLibroByID(Long ID, DetallesLibroModel libro)  throws NoSuchContenidoException{contenidoRepository.updateLibroByID(ID, libro);}
	
}
