package es.library.databaseserver.contenido.dao;

import java.util.List;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

public interface ContenidoDAO{

	//CONTENIDO BASICO
	public List<Contenido> getAllContenidos();
	
	public Contenido getContenidoByID(Long ID) throws NoSuchContenidoException;
	
	public void insertContenido(Contenido contenido) throws NotInsertedContenidoException;
	
	public void deleteContenidoByID(Long ID)  throws NoSuchContenidoException;
	
	public void updateContenidoByID(Long ID, Contenido contenido)  throws NoSuchContenidoException;
	
	
	//DETALLES AUDIOVISUAL
	public List<DetallesAudiovisualModel> getAllAudiovisual();
	
	public DetallesAudiovisualModel getAudiovisualByID(Long ID) throws NoSuchContenidoException;
	
	public void insertAudiovisual(DetallesAudiovisualModel audiovisual) throws NotInsertedContenidoException;
	
	public void deleteAudiovisualByID(Long ID)  throws NoSuchContenidoException;
	
	public void updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual)  throws NoSuchContenidoException;
	
	//DETALLES LIBROS
	public List<DetallesLibroModel> getAllLibro();
		
	public DetallesLibroModel getLibroByID(Long ID) throws NoSuchContenidoException;
		
	public void insertLibro(DetallesLibroModel libro) throws NotInsertedContenidoException;
		
	public void deleteLibroByID(Long ID) throws NoSuchContenidoException;
		
	public void updateLibroByID(Long ID, DetallesLibroModel libro)  throws NoSuchContenidoException;
}
