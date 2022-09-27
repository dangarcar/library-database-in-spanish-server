package es.library.databaseserver.personalspace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.search.controller.PrestamoSearchController;

@Service
public class MyPerfilServiceImpl implements MyPerfilService {

	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private PrestamoSearchController prestamoSearchService;
	
	@Override
	public Perfil getMyInfo(String username) {
		return perfilService.getPerfilByUsername(username);
	}

	@Override
	public List<Contenido> getHistorialDePrestamos(String username) {
		long idPerfil = perfilService.getPerfilByUsername(username).getID();
		return prestamoSearchService.getContenidosIdPerfil(idPerfil, true);
	}

	@Override
	public List<Prestamo> getMyPrestamos(String username) {
		long idPerfil = perfilService.getPerfilByUsername(username).getID();
		return prestamoSearchService.getPrestamosIdPerfil(idPerfil, null);
	}

	@Override
	public List<Contenido> getContenidosEnPrestamo(String username) {
		long idPerfil = perfilService.getPerfilByUsername(username).getID();
		return prestamoSearchService.getContenidosIdPerfil(idPerfil, false);
	}

	@Override
	public Perfil updateMyAccount(String username, Perfil perfil) {
		long idPerfil = perfilService.getPerfilByUsername(username).getID();
		perfil.setRole(getMyInfo(username).getRole());
		return perfilService.updatePerfilByID(idPerfil, perfil);
	}

}
