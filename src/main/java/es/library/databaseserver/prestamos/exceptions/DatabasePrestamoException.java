package es.library.databaseserver.prestamos.exceptions;

/**
 * Una excepcion para cuando no se ha podido modificar dicho prestamo en la base de datos
 * @author Daniel Garcia
 *
 */
public class DatabasePrestamoException extends RuntimeException{

	public DatabasePrestamoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabasePrestamoException(String message) {
		super(message);
	}
	
}
