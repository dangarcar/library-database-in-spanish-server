package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando el contenido ya existe en la base de datos
 * @author Daniel Garcia
 *
 */
public class ContenidoAlreadyExistsException extends RuntimeException{

	public ContenidoAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContenidoAlreadyExistsException(String message) {
		super(message);
	}

}
