package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando el soporte y el tipo del contenido no son compatibles
 * @author Daniel Garcia
 *
 */
public class NotValidSoporteException extends RuntimeException{

	public NotValidSoporteException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidSoporteException(String message) {
		super(message);
	}

}
