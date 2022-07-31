package es.library.databaseserver.perfil.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

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
		boolean afterYearZero = fecha.isAfter(LocalDate.of(0, 1, 1));
		boolean beforeNow = fecha.isBefore(LocalDate.now().minusYears(EDAD_MINIMA).plusDays(1));
		
		return afterYearZero && beforeNow;
	}
}
