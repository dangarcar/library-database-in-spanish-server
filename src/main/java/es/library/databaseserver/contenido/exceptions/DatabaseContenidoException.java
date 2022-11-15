package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando no se ha podido modificar dicho contenido en la base de datos
 * @author Daniel Garcia
 *
 */
public class DatabaseContenidoException extends RuntimeException{

	public DatabaseContenidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseContenidoException(String message) {
		super(message);
	}
	
}
