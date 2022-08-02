package es.library.databaseserver.perfil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando no existe dicho perfil en la base de datos
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PerfilNotFoundException extends RuntimeException{

	public PerfilNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PerfilNotFoundException(String message) {
		super(message);
	}

}
