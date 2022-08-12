package es.library.databaseserver.perfil.exceptions;

/**
 * Una excepcion para cuando no existe dicho perfil en la base de datos
 * @author Daniel Garcia
 *
 */
public class PerfilNotFoundException extends RuntimeException{

	public PerfilNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PerfilNotFoundException(String message) {
		super(message);
	}

}
