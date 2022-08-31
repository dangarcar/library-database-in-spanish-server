package es.library.databaseserver.security.exceptions;

/**
 * Excepción para cuando el refresh token no ha sido encontrado
 * 
 * @author Daniel García
 *
 */
public class RefreshTokenNotFoundException extends RuntimeException {

	public RefreshTokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RefreshTokenNotFoundException(String message) {
		super(message);
	}

	public RefreshTokenNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
