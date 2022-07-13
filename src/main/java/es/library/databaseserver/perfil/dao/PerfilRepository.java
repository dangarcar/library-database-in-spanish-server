package es.library.databaseserver.perfil.dao;

import java.sql.SQLException;
import java.util.List;

import es.library.databaseserver.perfil.Perfil;

public interface PerfilRepository{

	public List<Perfil> getAllPerfiles() throws SQLException;
	
	public Perfil getPerfilByDNI(int DNI) throws SQLException;
	
	public void insertPerfil(Perfil perfil) throws SQLException;
	
	public void deletePerfilByDNI(int DNI)  throws SQLException;
	
	public void updatePerfilByDNI(int DNI, Perfil perfil)  throws SQLException;
}
