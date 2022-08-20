package es.library.databaseserver.prestamos.transacciones.service;

import es.library.databaseserver.prestamos.Prestamo;

public interface PrestamosTransaccionesService {

	public static final String PERFIL_PREFIX = "PerfilException: ";
	public static final String CONTENIDO_PREFIX = "ContenidoException: ";
	
	public Prestamo prestar(Long contenidoId, Long perfilId);
	
	public Prestamo devolver(Long contenidoId, Long perfilId);
	
}
