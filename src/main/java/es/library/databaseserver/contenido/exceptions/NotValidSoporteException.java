package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando el soporte y el tipo del contenido no son compatibles
 * @author Daniel Garcia
 *
 */
public class NotValidSoporteException extends RuntimeException{

	public NotValidSoporteException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotValidSoporteException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
