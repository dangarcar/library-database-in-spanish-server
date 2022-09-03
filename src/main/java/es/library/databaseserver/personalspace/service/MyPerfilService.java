package es.library.databaseserver.personalspace.service;

import es.library.databaseserver.contenido.Contenido;
import java.util.List;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.prestamos.Prestamo;

public interface MyPerfilService {

	public Perfil getMyInfo(String username);
	
	public List<Contenido> getHistorialDePrestamos(String username);
	
	public List<Prestamo> getMyPrestamos(String username);
	
	public List<Contenido> getContenidosEnPrestamo(String username);
	
	public Perfil updateMyAccount(String username, Perfil perfil);
	
}
