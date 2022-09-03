package es.library.databaseserver.personalspace.service;

import es.library.databaseserver.prestamos.Prestamo;

public interface PrestamosService {
	
	public Prestamo prestar(Long contenidoId, String username);
	
	public Prestamo devolver(Long contenidoId, String username);
	
}
