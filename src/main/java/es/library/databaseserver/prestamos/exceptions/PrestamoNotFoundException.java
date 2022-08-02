package es.library.databaseserver.prestamos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando no existe dicho prestamo en la base de datos
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrestamoNotFoundException extends RuntimeException{

	public PrestamoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrestamoNotFoundException(String message) {
		super(message);
	}

}
