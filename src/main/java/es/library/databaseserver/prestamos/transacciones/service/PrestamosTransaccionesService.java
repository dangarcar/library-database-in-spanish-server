package es.library.databaseserver.prestamos.transacciones.service;

import es.library.databaseserver.prestamos.Prestamo;

public interface PrestamosTransaccionesService {

	public Prestamo prestar(Long contenidoId, Long perfilId);
	
	public Prestamo devolver(Long contenidoId, Long perfilId);
	
}
