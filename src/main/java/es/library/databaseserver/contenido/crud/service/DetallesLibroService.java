package es.library.databaseserver.contenido.crud.service;

import java.util.List;

import es.library.databaseserver.contenido.crud.model.DetallesLibroModel;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;

public interface DetallesLibroService {
	
	public List<DetallesLibroModel> getAllLibro();
		
	public DetallesLibroModel getLibroByID(long ID) throws ContenidoNotFoundException;
		
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws DatabaseContenidoException;
			
	public void deleteLibroByID(long ID) throws ContenidoNotFoundException;
			
	public DetallesLibroModel updateLibroByID(long ID, DetallesLibroModel libro)  throws ContenidoNotFoundException, DatabaseContenidoException;
}
