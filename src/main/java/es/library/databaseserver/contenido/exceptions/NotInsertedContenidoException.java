package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando no se ha podido insertar el contenido
 * @author Daniel Garcia
 *
 */
public class NotInsertedContenidoException extends RuntimeException{

	public NotInsertedContenidoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotInsertedContenidoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
