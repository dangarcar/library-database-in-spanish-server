package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando no existe dicho contenido en la base de datos
 * @author Daniel Garcia
 *
 */
public class NoSuchContenidoException extends RuntimeException{

	public NoSuchContenidoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoSuchContenidoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
