package es.library.databaseserver.prestamos.crud.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.exceptions.IllegalPrestamoException;

@Component
public class PrestamoValidator {
		
	public boolean validateFecha(LocalDateTime fecha) {
		boolean afterYearZero = fecha.isAfter(LocalDateTime.of(1, Month.JANUARY, 1,0,0));
		boolean beforeNow = fecha.isBefore(LocalDateTime.now());
		
		return afterYearZero && beforeNow;
	}
	
	public boolean validateDiasPrestamo(int dias) {
		return dias>0;
	}
	
	public boolean validateFechasPrestamoDevolucion(LocalDateTime prestamo, LocalDateTime devolucion) {
		return prestamo.isBefore(devolucion);
	}
	
	public void validatePrestamoCorrect(Prestamo prestamo) throws IllegalPrestamoException{
		StringBuilder errorBuilder = new StringBuilder();

		if(!validateFecha(prestamo.getFechaHoraPrestamo())) {
			errorBuilder.append(System.lineSeparator());
			errorBuilder.append("La fecha de prestamo ");
			errorBuilder.append(prestamo.getFechaHoraPrestamo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
			errorBuilder.append(" no es valida");
		}
		if(!validateDiasPrestamo(prestamo.getDiasdePrestamo())) {
			errorBuilder.append(System.lineSeparator());
			errorBuilder.append("Un contenido no puede ser prestado durante ");
			errorBuilder.append(prestamo.getDiasdePrestamo());
			errorBuilder.append(" dias");
		}
		if(prestamo.isDevuelto()) {
			if(!validateFecha(prestamo.getFechaHoraDevolucion())) {
				errorBuilder.append(System.lineSeparator());
				errorBuilder.append("La fecha de devolucion ");
				errorBuilder.append(prestamo.getFechaHoraDevolucion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
				errorBuilder.append(" no es valida");
			}
			if(!validateFechasPrestamoDevolucion(prestamo.getFechaHoraPrestamo(), prestamo.getFechaHoraDevolucion())) {
				errorBuilder.append(System.lineSeparator());
				errorBuilder.append("La fecha de prestamo debe ser anterior a la de devolucion. Fecha de prestamo: ");
				errorBuilder.append(prestamo.getFechaHoraPrestamo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")));
				errorBuilder.append(" Fecha de devolucion: ");
				errorBuilder.append(prestamo.getFechaHoraDevolucion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")));
			}
		}
		
		//Si el StringBuilder no esta vacio, lanzo una excepcion de perfil ilegal
		if(!errorBuilder.isEmpty()) throw new IllegalPrestamoException(errorBuilder.toString());
	}
}
