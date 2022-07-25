package es.library.databaseserver.contenido.service;

import java.util.List;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;

public interface ContenidoService {

	public List<Contenido> getAllContenidos();
	
	public Contenido getContenidoByID(long ID) throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException;
	
	public Contenido insertContenido(Contenido contenido) throws NotInsertedContenidoException, NotValidTypeContenidoException, NotValidSoporteException, ContenidoAlreadyExistsException;
	
	public Contenido deleteContenidoByID(long ID)  throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException;
	
	public Contenido updateContenidoByID(long ID, Contenido contenido) throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException, NotInsertedContenidoException;
	
}
