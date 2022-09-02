package es.library.databaseserver.security.exceptions;

/**
 * Excepción para cuando algo de la autorización de un usuario falla
 * 
 * @author Daniel García
 *
 */
public class AuthorizationException extends RuntimeException{

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationException(String message) {
		super(message);
	}
	
}
