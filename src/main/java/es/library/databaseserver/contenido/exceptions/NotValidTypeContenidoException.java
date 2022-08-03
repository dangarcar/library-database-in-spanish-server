package es.library.databaseserver.contenido.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando el tipo del contenido no es correcto
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidTypeContenidoException extends RuntimeException {

	public NotValidTypeContenidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidTypeContenidoException(String message) {
		super(message);
	}

}
