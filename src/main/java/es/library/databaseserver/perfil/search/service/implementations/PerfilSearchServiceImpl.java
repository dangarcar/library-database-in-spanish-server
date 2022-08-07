package es.library.databaseserver.perfil.search.service.implementations;

import static es.library.databaseserver.utils.Utils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;
import es.library.databaseserver.perfil.search.dao.PerfilSearchDAO;
import es.library.databaseserver.perfil.search.service.PerfilSearchService;

@Service
public class PerfilSearchServiceImpl implements PerfilSearchService{

	@Autowired
	private PerfilService crudService;
	
	@Autowired
	private PerfilSearchDAO searchDAO;
	
	@Override
	public Perfil getPerfilByID(Long id) throws PerfilNotFoundException {
		return crudService.getPerfilByID(id);
	}

	@Override
	public List<Perfil> getAllPerfiles() {
		return crudService.getAllPerfiles();
	}

	@Override
	public List<Perfil> getPerfilesByNombre(String nombre) {
		return crudService.idListToPerfilList(searchDAO.getPerfilesByNombre(nombre));
	}

	@Override
	public List<Perfil> getPerfilesByEmail(String email) {
		return crudService.idListToPerfilList(searchDAO.getPerfilesByEmail(email));
	}

	@Override
	public List<Perfil> getPerfilesByNacimiento(String nacimiento) {
		return crudService.idListToPerfilList(searchDAO.getPerfilesByNacimiento(nacimiento));
	}

	@Override
	public List<Perfil> getPerfilesByPrompt(String prompt) {
		return crudService.idListToPerfilList(searchDAO.getPerfilesByPrompt(prompt));
	}

	@Override
	public List<Perfil> getAllAdmins() {
		return crudService.idListToPerfilList(searchDAO.getAllAdmins());
	}

	public List<Perfil> getPerfilesByMultipleParams(String nombre, String email, LocalDate fromNacimiento, LocalDate toNacimiento, Boolean admin) {
		List<Set<Perfil>> perfilSet = new ArrayList<>();
		
		if(nombre != null) perfilSet.add(new HashSet<>(getPerfilesByNombre(nombre)));
		
		if(email != null) perfilSet.add(new HashSet<>(getPerfilesByEmail(email)));
		
		if(fromNacimiento != null || toNacimiento != null) perfilSet.add(new HashSet<>(getPerfilesBetweenTwoBirthDates(fromNacimiento, toNacimiento)));
		
		return filterPerfilAdmin(intersection(perfilSet).stream().toList(), admin);
	}
	
	public List<Perfil> filterPerfilAdmin(List<Perfil> perfs, Boolean admin) {
		if(admin == null) return perfs;
		
		return perfs.stream()
				.filter(p -> p.isAdmin() == admin)
				.toList();
	}
	
	public List<Perfil> getPerfilesBetweenTwoBirthDates(LocalDate from, LocalDate to) {
		if(from == null) from = LocalDate.MIN;
		if(to == null) to = LocalDate.of(9999, 12, 31);
		
		return crudService.idListToPerfilList(searchDAO.getPerfilesBetweenTwoBirthDates(from, to));
	}
	
}
