package es.library.databaseserver.perfil.search.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.model.PerfilResponse;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;

public interface PerfilSearchService {

	public PerfilResponse getPerfilByID(Long id) throws PerfilNotFoundException;
	
	public PerfilResponse getPerfilByUsername(String username) throws PerfilNotFoundException;
	
	public List<PerfilResponse> getAllPerfiles();
	
	public List<PerfilResponse> getPerfilesByNombre(String nombre);
	
	public List<PerfilResponse> getPerfilesByEmail(String email);
	
	public List<PerfilResponse> getPerfilesByNacimiento(String nacimiento);
	
	public List<PerfilResponse> getPerfilesByPrompt(String prompt);
	
	public List<PerfilResponse> getPerfilesByMultipleParams(String query, String nombre, String email, LocalDate fromNacimiento, LocalDate toNacimiento, Roles roles);

	public List<PerfilResponse> getPerfilesByRole(Roles roles);
	
	public static List<PerfilResponse> perfilListToPerfilResponseList(List<Perfil> perfiles) {
		return perfiles.stream()
				.map(PerfilResponse::new)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
}
