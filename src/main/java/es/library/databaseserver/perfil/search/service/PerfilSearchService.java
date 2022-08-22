package es.library.databaseserver.perfil.search.service;

import java.time.LocalDate;
import java.util.List;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;

public interface PerfilSearchService {

	public Perfil getPerfilByID(Long id) throws PerfilNotFoundException;
	
	public List<Perfil> getAllPerfiles();
	
	public List<Perfil> getPerfilesByNombre(String nombre);
	
	public List<Perfil> getPerfilesByEmail(String email);
	
	public List<Perfil> getPerfilesByNacimiento(String nacimiento);
	
	public List<Perfil> getPerfilesByPrompt(String prompt);
	
	public List<Perfil> getPerfilesByMultipleParams(String query, String nombre, String email, LocalDate fromNacimiento, LocalDate toNacimiento, Roles roles);

	public List<Perfil> getPerfilesByRole(Roles roles);
	
}
