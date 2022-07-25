package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando el tipo del contenido no es correcto
 * @author Daniel Garcia
 *
 */
public class NotValidTypeContenidoException extends RuntimeException {

	public NotValidTypeContenidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidTypeContenidoException(String message) {
		super(message);
	}

}
