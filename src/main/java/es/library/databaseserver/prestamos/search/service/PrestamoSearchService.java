package es.library.databaseserver.prestamos.search.service;

import java.time.LocalDateTime;
import java.util.List;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;
import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;

public interface PrestamoSearchService {

	public List<Prestamo> getAllPrestamos();
	
	public Prestamo getPrestamoByID(Long id) throws PrestamoNotFoundException;
	
	public List<Prestamo> getPrestamosByIdContenido(Long id) throws ContenidoNotFoundException;
	
	public List<Prestamo> getPrestamosIdPerfil(Long id) throws PerfilNotFoundException;
	
	public List<Prestamo> getPrestamosByDiasDePrestamo(int dias);
	
	public List<Prestamo> getPrestamosByFechaPrestamo(String fecha);
	
	public List<Prestamo> getPrestamosByFechaDevolucion(String fecha);
	
	public List<Prestamo> getPrestamosBetweenTwoPrestamoDates(LocalDateTime from, LocalDateTime to);
	
	public List<Prestamo> getPrestamosBetweenTwoDevolucionDates(LocalDateTime from, LocalDateTime to);
	
	public List<Prestamo> getPrestamoByMultipleParams(Long idContenido,Long idPerfil,Integer dias,LocalDateTime fromPrestamo,LocalDateTime toPrestamo,
			LocalDateTime fromDevolucion,LocalDateTime toDevolucion,Boolean d);
	
	public List<Contenido> getContenidosByPerfilID(Long idPerfil, Boolean d);
	
	public static List<Prestamo> filterDevueltosPrestamos(List<Prestamo> prests, Boolean devueltos) {
		if(devueltos == null) return prests;
		
		return prests.stream()
				.filter(p -> p.isDevuelto() == devueltos)
				.toList();
	}
}
