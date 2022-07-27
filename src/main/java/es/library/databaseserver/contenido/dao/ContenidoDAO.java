package es.library.databaseserver.contenido.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;

public interface ContenidoDAO{

	public List<Long> getAllContenidosID();
	
	public Optional<Contenido> getContenidoByID(Long ID);
	
	public Contenido insertContenido(Contenido contenido) throws DatabaseContenidoException,ContenidoAlreadyExistsException;
	
	public void deleteContenidoByID(Long ID)  throws ContenidoNotFoundException;
	
	public Contenido updateContenidoByID(Long ID, Contenido contenido)  throws ContenidoNotFoundException;

}
