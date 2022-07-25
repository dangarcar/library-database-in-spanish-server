package es.library.databaseserver.contenido.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;

public interface ContenidoDAO{

	public List<Long> getAllContenidosID();
	
	public Optional<Contenido> getContenidoByID(Long ID);
	
	public Contenido insertContenido(Contenido contenido) throws NotInsertedContenidoException,ContenidoAlreadyExistsException;
	
	public Contenido deleteContenidoByID(Long ID)  throws NoSuchContenidoException;
	
	public Contenido updateContenidoByID(Long ID, Contenido contenido)  throws NoSuchContenidoException;

}
