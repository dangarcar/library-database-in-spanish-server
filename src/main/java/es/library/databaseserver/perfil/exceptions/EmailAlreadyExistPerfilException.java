package es.library.databaseserver.perfil.exceptions;

/**
 * Excepcion para cuando el correo electrónico del perfil ya existe en la base de datos
 * @author Daniel Garcia
 *
 */
public class EmailAlreadyExistPerfilException extends RuntimeException{

	public EmailAlreadyExistPerfilException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailAlreadyExistPerfilException(String message) {
		super(message);
	}
	
}
