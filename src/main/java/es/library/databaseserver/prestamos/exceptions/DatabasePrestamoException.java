package es.library.databaseserver.prestamos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando no se ha podido modificar dicho prestamo en la base de datos
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabasePrestamoException extends RuntimeException{

	public DatabasePrestamoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabasePrestamoException(String message) {
		super(message);
	}
	
}
