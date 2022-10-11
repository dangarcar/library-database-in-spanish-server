package es.library.databaseserver.prestamos.crud.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.search.service.ContenidoSearchService;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;
import es.library.databaseserver.perfil.search.service.PerfilSearchService;
import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.exceptions.IllegalPrestamoException;

@Component
public class PrestamoValidator {
	
	@Autowired
	private ContenidoSearchService contenidoSearchService;
	
	@Autowired
	private PerfilSearchService perfilSearchService;
	
	public boolean validatePerfil(long id) {
		try {
			perfilSearchService.getPerfilByID(id);
			return true;
		} catch (PerfilNotFoundException e) {
			return false;
		}
	}
	
	public boolean validateContenido(long id) {
		try {
			contenidoSearchService.getContenidoById(id);
			return true;
		} catch (ContenidoNotFoundException e) {
			return false;
		}
	}
	
	public boolean validateFecha(ZonedDateTime fecha) {
		return fecha.minusMinutes(1).isBefore(ZonedDateTime.now()) || fecha.minusMinutes(1).isEqual(ZonedDateTime.now());
	}
	
	public boolean validateDiasPrestamo(int dias) {
		return dias>0;
	}
	
	public boolean validateFechasPrestamoDevolucion(ZonedDateTime prestamo, ZonedDateTime devolucion) {
		return prestamo.isBefore(devolucion) || prestamo.isEqual(devolucion);
	}
	
	public void validatePrestamoCorrect(Prestamo prestamo) throws IllegalPrestamoException{
		StringBuilder errorBuilder = new StringBuilder();

		if(!validatePerfil(prestamo.getIDPerfil())) {
			errorBuilder.append(System.lineSeparator());
			errorBuilder.append("El perfil con id: ");
			errorBuilder.append(prestamo.getIDPerfil());
			errorBuilder.append(" no existe");
		}
		
		if(!validateContenido(prestamo.getIDContenido())) {
			errorBuilder.append(System.lineSeparator());
			errorBuilder.append("El contenido con id: ");
			errorBuilder.append(prestamo.getIDContenido());
			errorBuilder.append(" no existe");
		}
		
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
