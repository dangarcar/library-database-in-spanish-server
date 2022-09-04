package es.library.databaseserver.security.exceptions;

/**
 * Excepción para cuando el refresh token esta caducado
 * 
 * @author Daniel García
 *
 */
public class ExpiredTokenException extends RuntimeException{

	public ExpiredTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpiredTokenException(String message) {
		super(message);
	}

	public ExpiredTokenException(Throwable cause) {
		super(cause);
	}
	
}
