package es.library.databaseserver.perfil.search.service;

import java.time.LocalDate;
import java.util.List;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;

public interface PerfilSearchService {

	public Perfil getPerfilByID(Long id) throws PerfilNotFoundException;
	
	public List<Perfil> getAllPerfiles();
	
	public List<Perfil> getPerfilesByNombre(String nombre);
	
	public List<Perfil> getPerfilesByEmail(String email);
	
	public List<Perfil> getPerfilesByNacimiento(String nacimiento);
	
	public List<Perfil> getPerfilesByPrompt(String prompt);

	public List<Perfil> getAllAdmins();
	
	public List<Perfil> getPerfilesByMultipleParams(String nombre, String email, LocalDate fromNacimiento, LocalDate toNacimiento, Boolean admin);

	public static List<Perfil> filterPerfilAdmin(List<Perfil> perfs, Boolean admin) {
		if(admin == null) return perfs;
		
		return perfs.stream()
				.filter(p -> p.isAdmin() == admin)
				.toList();
	}
	
}
