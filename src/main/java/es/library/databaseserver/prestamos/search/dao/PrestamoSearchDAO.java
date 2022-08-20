package es.library.databaseserver.prestamos.search.dao;

import java.time.LocalDateTime;
import java.util.List;

public interface PrestamoSearchDAO {

	public List<Long> getPrestamosByIdContenido(Long id);
	
	public List<Long> getPrestamosIdPerfil(Long id);
	
	public List<Long> getPrestamosByDiasDePrestamo(int dias);
	public List<Long> getPrestamosByDiasDePrestamo(int min, int max);
	
	public List<Long> getPrestamosByFechaPrestamo(String prestamo);
	
	public List<Long> getPrestamosByFechaDevolucion(String devolucion);
	
	public List<Long> getPrestamosBetweenTwoPrestamoDates(LocalDateTime from, LocalDateTime to);
	
	public List<Long> getPrestamosBetweenTwoDevolucionDates(LocalDateTime from, LocalDateTime to);
	
	public List<Long> getContenidosMasPrestados(int nContenidos);
}
