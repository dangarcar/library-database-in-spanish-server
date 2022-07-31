package es.library.databaseserver.perfil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Una excepcion para cuando el contenido ya existe en la base de datos
 * @author Daniel Garcia
 *
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class PerfilAlreadyExistsException extends RuntimeException{

	public PerfilAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public PerfilAlreadyExistsException(String message) {
		super(message);
	}

}
