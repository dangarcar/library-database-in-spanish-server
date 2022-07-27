package es.library.databaseserver.contenido.service;

import java.util.List;

import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

public interface DetallesLibroService {
	
	public List<DetallesLibroModel> getAllLibro();
		
	public DetallesLibroModel getLibroByID(long ID) throws ContenidoNotFoundException;
		
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws DatabaseContenidoException;
			
	public void deleteLibroByID(long ID) throws ContenidoNotFoundException;
			
	public DetallesLibroModel updateLibroByID(long ID, DetallesLibroModel libro)  throws ContenidoNotFoundException, DatabaseContenidoException;
}
