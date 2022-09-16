package es.library.databaseserver.security.exceptions;

/**
 * Excepción para cuando el refresh token esta caducado
 * 
 * @author Daniel García
 *
 */
public class ExpiredRefreshTokenException extends RuntimeException{

	public ExpiredRefreshTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpiredRefreshTokenException(String message) {
		super(message);
	}

	public ExpiredRefreshTokenException(Throwable cause) {
		super(cause);
	}
	
}
