package es.library.databaseserver.prestamos.exceptions;

/**
 * Excepcion para cuando un prestamo tiene algun parametro no permitido
 * @author Daniel Garcia
 *
 */
public class IllegalPrestamoException extends RuntimeException{

	public IllegalPrestamoException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalPrestamoException(String message) {
		super(message);
	}

}
