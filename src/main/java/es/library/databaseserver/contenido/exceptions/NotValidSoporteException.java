package es.library.databaseserver.contenido.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando el soporte y el tipo del contenido no son compatibles
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidSoporteException extends RuntimeException{

	public NotValidSoporteException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidSoporteException(String message) {
		super(message);
	}

}
