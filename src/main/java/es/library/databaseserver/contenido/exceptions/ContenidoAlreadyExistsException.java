package es.library.databaseserver.contenido.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando el contenido ya existe en la base de datos
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ContenidoAlreadyExistsException extends RuntimeException{

	public ContenidoAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContenidoAlreadyExistsException(String message) {
		super(message);
	}

}
