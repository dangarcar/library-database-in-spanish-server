package es.library.databaseserver.perfil.crud.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.exceptions.IllegalPerfilException;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;

public interface PerfilDAO{

	public List<Long> getAllPerfilesID();
	
	public Optional<Perfil> getPerfilByID(Long ID);
	
	public Perfil insertPerfil(Perfil perfil) throws IllegalPerfilException;
	
	public void deletePerfilByID(Long ID) throws PerfilNotFoundException;
	
	public Perfil updatePerfilByID(Long ID, Perfil perfil) throws IllegalPerfilException,PerfilNotFoundException;

}
