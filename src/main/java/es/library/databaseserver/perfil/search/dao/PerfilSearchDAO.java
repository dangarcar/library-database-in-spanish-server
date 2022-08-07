package es.library.databaseserver.perfil.search.dao;

import java.time.LocalDate;
import java.util.List;

public interface PerfilSearchDAO {
	
	public List<Long> getPerfilesByNombre(String nombre);
	
	public List<Long> getPerfilesByEmail(String email);
	
	public List<Long> getPerfilesByNacimiento(String nacimiento);
	
	public List<Long> getPerfilesByPrompt(String prompt);

	public List<Long> getAllAdmins();
	
	public List<Long> getPerfilesBetweenTwoBirthDates(LocalDate from, LocalDate to);
	
}
