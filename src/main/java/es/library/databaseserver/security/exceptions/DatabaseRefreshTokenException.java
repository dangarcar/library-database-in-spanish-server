package es.library.databaseserver.security.exceptions;

/**
 * Excepción para las bases de datos de los refresh tokens
 * 
 * @author Daniel García
 *
 */
public class DatabaseRefreshTokenException extends RuntimeException{

	public DatabaseRefreshTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseRefreshTokenException(String message) {
		super(message);
	}

	public DatabaseRefreshTokenException(Throwable cause) {
		super(cause);
	}
	
}
