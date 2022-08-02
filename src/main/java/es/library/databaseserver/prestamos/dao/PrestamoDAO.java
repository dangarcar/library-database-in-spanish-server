package es.library.databaseserver.prestamos.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;

public interface PrestamoDAO {
	
	public List<Long> getAllPrestamosID();
	
	public Optional<Prestamo> getPrestamoByID(Long ID);
	
	public Prestamo insertPrestamo(Prestamo prestamo);
	
	public void deletePrestamoByID(Long ID) throws PrestamoNotFoundException;
	
	public Prestamo updatePrestamoByID(Long ID, Prestamo prestamo) throws PrestamoNotFoundException;
	
}
