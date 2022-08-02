package es.library.databaseserver.prestamos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion para cuando un prestamo tiene algun parametro no permitido
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalPrestamoException extends RuntimeException{

	public IllegalPrestamoException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalPrestamoException(String message) {
		super(message);
	}

}
