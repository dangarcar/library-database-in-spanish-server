package es.library.databaseserver.perfil.crud.service;

import java.util.List;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.exceptions.EmailAlreadyExistPerfilException;
import es.library.databaseserver.perfil.exceptions.IllegalPerfilException;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;

public interface PerfilService {

	public List<Perfil> getAllPerfiles();
	
	public Perfil getPerfilByID(Long id) throws PerfilNotFoundException;
	
	public Perfil insertPerfil(Perfil perfil) throws IllegalPerfilException, EmailAlreadyExistPerfilException;
	
	public void deletePerfilByID(Long id) throws PerfilNotFoundException;
	
	public Perfil updatePerfilByID(Long id, Perfil perfil) throws IllegalPerfilException, PerfilNotFoundException, EmailAlreadyExistPerfilException;

	public List<Perfil> idListToPerfilList(List<Long> ids);
	
	public void makePerfilAdmin(Long id) throws PerfilNotFoundException;
	
	public void makeAdminPerfil(Long id) throws PerfilNotFoundException;
	
}
