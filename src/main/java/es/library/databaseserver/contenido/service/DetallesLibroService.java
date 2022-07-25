package es.library.databaseserver.contenido.service;

import java.util.List;

import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

public interface DetallesLibroService {
	
	public List<DetallesLibroModel> getAllLibro();
		
	public DetallesLibroModel getLibroByID(long ID) throws NoSuchContenidoException;
		
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws NotInsertedContenidoException;
			
	public DetallesLibroModel deleteLibroByID(long ID) throws NoSuchContenidoException;
			
	public DetallesLibroModel updateLibroByID(long ID, DetallesLibroModel libro)  throws NoSuchContenidoException, NotInsertedContenidoException;
}
