package es.library.databaseserver.contenido.exceptions;

/**
 * Excepcion para cuando un contenido tiene algun parametro no permitido
 * @author Daniel Garcia
 *
 */
public class IllegalContenidoException extends RuntimeException{

	public IllegalContenidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalContenidoException(String message) {
		super(message);
	}

}
