package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando no existe dicho contenido en la base de datos
 * @author Daniel Garcia
 *
 */
public class ContenidoNotFoundException extends RuntimeException{

	public ContenidoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContenidoNotFoundException(String message) {
		super(message);
	}

}
