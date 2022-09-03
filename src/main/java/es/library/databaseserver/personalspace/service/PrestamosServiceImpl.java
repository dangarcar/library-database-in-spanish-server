package es.library.databaseserver.personalspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.transacciones.service.PrestamosTransaccionesService;

@Service
public class PrestamosServiceImpl implements PrestamosService {

	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private PrestamosTransaccionesService transaccionesService;
	
	@Override
	public Prestamo prestar(Long contenidoId, String username) {
		long idPerfil = perfilService.getPerfilByUsername(username).getID();
		
		return transaccionesService.prestar(contenidoId, idPerfil);
	}

	@Override
	public Prestamo devolver(Long contenidoId, String username) {
		long idPerfil = perfilService.getPerfilByUsername(username).getID();
		
		return transaccionesService.devolver(contenidoId, idPerfil);
	}


}
