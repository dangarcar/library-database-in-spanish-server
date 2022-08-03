package es.library.databaseserver.contenido.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando no existe dicho contenido en la base de datos
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContenidoNotFoundException extends RuntimeException{

	public ContenidoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContenidoNotFoundException(String message) {
		super(message);
	}

}
