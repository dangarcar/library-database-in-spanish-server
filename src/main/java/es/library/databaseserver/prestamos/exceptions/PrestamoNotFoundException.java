package es.library.databaseserver.prestamos.exceptions;

/**
 * Una excepcion para cuando no existe dicho prestamo en la base de datos
 * @author Daniel Garcia
 *
 */
public class PrestamoNotFoundException extends RuntimeException{

	public PrestamoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrestamoNotFoundException(String message) {
		super(message);
	}

}
