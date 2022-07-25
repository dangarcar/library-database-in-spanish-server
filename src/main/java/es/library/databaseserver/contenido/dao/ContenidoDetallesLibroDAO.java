package es.library.databaseserver.contenido.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

public interface ContenidoDetallesLibroDAO {
	
	public List<Long> getAllLibroID();
	
	public Optional<DetallesLibroModel> getLibroByID(Long ID);
		
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws NotInsertedContenidoException;
		
	public DetallesLibroModel deleteLibroByID(Long ID) throws NoSuchContenidoException;
		
	public DetallesLibroModel updateLibroByID(Long ID, DetallesLibroModel libro) throws NoSuchContenidoException;
	
}
