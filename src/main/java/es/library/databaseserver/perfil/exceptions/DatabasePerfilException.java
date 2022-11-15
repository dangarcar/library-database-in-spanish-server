package es.library.databaseserver.perfil.exceptions;

/**
 * Una excepcion para cuando no se ha podido modificar dicho perfil en la base de datos
 * @author Daniel Garcia
 *
 */
public class DatabasePerfilException extends RuntimeException{

	public DatabasePerfilException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabasePerfilException(String message) {
		super(message);
	}
	
}
