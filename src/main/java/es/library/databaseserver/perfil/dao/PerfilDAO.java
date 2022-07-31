package es.library.databaseserver.perfil.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.perfil.Perfil;

public interface PerfilDAO{

	public List<Long> getAllPerfilesID();
	
	public Optional<Perfil> getPerfilByID(Long ID);
	
	public Perfil insertPerfil(Perfil perfil);
	
	public void deletePerfilByID(Long ID);
	
	public Perfil updatePerfilByID(Long ID, Perfil perfil);

}
