package es.library.databaseserver.prestamos.exceptions;

public class PrestamoNotAllowedException extends RuntimeException{

	public PrestamoNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrestamoNotAllowedException(String message) {
		super(message);
	}

	
}
