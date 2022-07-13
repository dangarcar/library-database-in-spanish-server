package es.library.databaseserver.contenido.exceptions;

/**
 * Una excepcion para cuando no existe dicho contenido en la base de datos
 * @author Daniel Garcia
 *
 */
public class NoSuchContenidoException extends Exception{
	private static final long serialVersionUID = 944949807237723239L;

	public NoSuchContenidoException(String message) {
		super(message);
	}
	
}
