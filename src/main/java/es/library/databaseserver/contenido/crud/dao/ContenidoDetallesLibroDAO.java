package es.library.databaseserver.contenido.crud.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.contenido.crud.model.DetallesLibroModel;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;

public interface ContenidoDetallesLibroDAO {
	
	public List<Long> getAllLibroID();
	
	public Optional<DetallesLibroModel> getLibroByID(Long ID);
	
	public Optional<DetallesLibroModel> getLibroIfIdIsNull(DetallesLibroModel libroIdNull);
		
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws DatabaseContenidoException;
		
	public DetallesLibroModel deleteLibroByID(Long ID) throws ContenidoNotFoundException;
		
	public DetallesLibroModel updateLibroByID(Long ID, DetallesLibroModel libro) throws ContenidoNotFoundException;

	public void deleteLibroByIDIfIsNotPointed(Long ID, boolean ifOne) throws ContenidoNotFoundException;

}
