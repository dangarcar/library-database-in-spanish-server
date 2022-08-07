package es.library.databaseserver.prestamos.search.service;

import java.time.LocalDateTime;
import java.util.List;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;

public interface PrestamoSearchService {

	public List<Prestamo> getAllPrestamos();
	
	public Prestamo getPrestamoByID(Long id) throws PrestamoNotFoundException;
	
	public List<Prestamo> getPrestamosByIdContenido(Long id);
	
	public List<Prestamo> getPrestamosIdPerfil(Long id);
	
	public List<Prestamo> getPrestamosByDiasDePrestamo(int dias);
	
	public List<Prestamo> getPrestamosByFechaPrestamo(String fecha);
	
	public List<Prestamo> getPrestamosByFechaDevolucion(String fecha);
	
	public List<Prestamo> getPrestamosBetweenTwoPrestamoDates(LocalDateTime from, LocalDateTime to);
	
	public List<Prestamo> getPrestamosBetweenTwoDevolucionDates(LocalDateTime from, LocalDateTime to);
	
	public List<Prestamo> getPrestamoByMultipleParams(Long idContenido,Long idPerfil,Integer dias,LocalDateTime fromPrestamo,LocalDateTime toPrestamo,
			LocalDateTime fromDevolucion,LocalDateTime toDevolucion,Boolean d);
	
	public List<Prestamo> filterDevueltosPrestamos(List<Prestamo> prests, Boolean devueltos);
}
