package es.library.databaseserver.perfil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion para cuando un contenido tiene algun parametro no permitido
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalPerfilException extends RuntimeException{

	public IllegalPerfilException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalPerfilException(String message) {
		super(message);
	}

}
