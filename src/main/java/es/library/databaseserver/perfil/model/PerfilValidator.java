package es.library.databaseserver.perfil.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.exceptions.IllegalPerfilException;

@Component
public class PerfilValidator {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";	
	private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$";
	private static final int EDAD_MINIMA = 12;
	
	public boolean validateEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();   
	}
	
	public boolean validatePassword(String password) {
		return Pattern.compile(PASSWORD_REGEX).matcher(password).matches(); 
	}
	
	public boolean validateEdad(int edad) {
		return edad >= EDAD_MINIMA;
	}
	
	public boolean validateFechaNacimiento(LocalDate fecha) {
		boolean afterYearZero = fecha.isAfter(LocalDate.of(1, 1, 1));
		boolean beforeNow = fecha.isBefore(LocalDate.now().minusYears(EDAD_MINIMA).plusDays(1));
		
		return afterYearZero && beforeNow;
	}
	
	public void validatePerfilCorrect(Perfil perfil) throws IllegalPerfilException{
		StringBuilder errorBuilder = new StringBuilder();

		if(!validateEmail(perfil.getCorreoElectronico())) {
			errorBuilder.append(System.lineSeparator());
			errorBuilder.append("El email ");
			errorBuilder.append(perfil.getCorreoElectronico());
			errorBuilder.append(" no es valido");
		}
		if(!validateFechaNacimiento(perfil.getFechaNacimiento())) {
			errorBuilder.append(System.lineSeparator());
			errorBuilder.append("La fecha ");
			errorBuilder.append(perfil.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			errorBuilder.append(" no es valida");
		}
		if(!validatePassword(perfil.getContrasena())) {
			errorBuilder.append(System.lineSeparator());
			errorBuilder.append("La contraseña no es valida");
		}

		//Si el StringBuilder no esta vacio, lanzo una excepcion de perfil ilegal
		if(!errorBuilder.isEmpty()) throw new IllegalPerfilException(errorBuilder.toString());
	}
}
