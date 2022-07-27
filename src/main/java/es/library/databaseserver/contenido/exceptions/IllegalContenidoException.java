package es.library.databaseserver.contenido.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion para cuando un contenido tiene algun parametro no permitido
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalContenidoException extends RuntimeException{

	public IllegalContenidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalContenidoException(String message) {
		super(message);
	}

}
