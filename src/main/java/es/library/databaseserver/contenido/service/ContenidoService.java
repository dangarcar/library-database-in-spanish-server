package es.library.databaseserver.contenido.service;

import java.util.List;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;

public interface ContenidoService {

	public List<Contenido> getAllContenidos();
	
	public Contenido getContenidoByID(long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException;
	
	public Contenido insertContenido(Contenido contenido) throws DatabaseContenidoException, NotValidTypeContenidoException, NotValidSoporteException, ContenidoAlreadyExistsException;
	
	public void deleteContenidoByID(long ID)  throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException;
	
	public Contenido updateContenidoByID(long ID, Contenido contenido) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException, DatabaseContenidoException, ContenidoAlreadyExistsException;
	
}
