package es.library.databaseserver.prestamos.service;

import java.util.List;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.exceptions.IllegalPrestamoException;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;

public interface PrestamoService {

	public List<Prestamo> getAllPrestamos();
	
	public Prestamo getPrestamoByID(Long id) throws PrestamoNotFoundException;
	
	public Prestamo insertPrestamo(Prestamo prestamo) throws IllegalPrestamoException;
	
	public void deletePrestamoByID(Long id) throws PrestamoNotFoundException;
	
	public Prestamo updatePrestamoByID(Long id, Prestamo prestamo) throws IllegalPrestamoException, PrestamoNotFoundException;
	
}
