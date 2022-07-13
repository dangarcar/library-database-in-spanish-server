package es.library.databaseserver.contenido.dao;

import java.util.List;

import es.library.databaseserver.contenido.exceptions.ContenidoNotInsertedException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModel;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

public interface ContenidoDAO{

	//CONTENIDO BASICO
	public List<ContenidoModel> getAllContenidos();
	
	public ContenidoModel getContenidoByID(Long ID) throws NoSuchContenidoException;
	
	public void insertContenido(ContenidoModel contenido) throws ContenidoNotInsertedException;
	
	public void deleteContenidoByID(Long ID)  throws NoSuchContenidoException;
	
	public void updateContenidoByID(Long ID, ContenidoModel contenido)  throws NoSuchContenidoException;
	
	
	//DETALLES AUDIOVISUAL
	public List<DetallesAudiovisualModel> getAllAudiovisual();
	
	public DetallesAudiovisualModel getAudiovisualByID(Long ID) throws NoSuchContenidoException;
	
	public void insertAudiovisual(DetallesAudiovisualModel audiovisual) throws ContenidoNotInsertedException;
	
	public void deleteAudiovisualByID(Long ID)  throws NoSuchContenidoException;
	
	public void updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual)  throws NoSuchContenidoException;
	
	//DETALLES LIBROS
	public List<DetallesLibroModel> getAllLibro();
		
	public DetallesLibroModel getLibroByID(Long ID) throws NoSuchContenidoException;
		
	public void insertLibro(DetallesLibroModel libro) throws ContenidoNotInsertedException;
		
	public void deleteLibroByID(Long ID) throws NoSuchContenidoException;
		
	public void updateLibroByID(Long ID, DetallesLibroModel libro)  throws NoSuchContenidoException;
}
