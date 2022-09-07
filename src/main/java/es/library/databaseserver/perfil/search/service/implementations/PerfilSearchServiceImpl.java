package es.library.databaseserver.perfil.search.service.implementations;

import static es.library.databaseserver.shared.Utils.intersection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.model.PerfilResponse;
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
	public PerfilResponse getPerfilByID(Long id) throws PerfilNotFoundException {
		return new PerfilResponse(crudService.getPerfilByID(id));
	}

	@Override
	public PerfilResponse getPerfilByUsername(String username) throws PerfilNotFoundException {
		return new PerfilResponse(crudService.getPerfilByUsername(username));
	}
	
	@Override
	public List<PerfilResponse> getAllPerfiles() {
		return PerfilSearchService.perfilListToPerfilResponseList(
				crudService.getAllPerfiles());
	}

	@Override
	public List<PerfilResponse> getPerfilesByNombre(String nombre) {
		nombre = nombre.replace("-", " ");
		
		return PerfilSearchService.perfilListToPerfilResponseList(
				crudService.idListToPerfilList(searchDAO.getPerfilesByNombre(nombre)));
	}

	@Override
	public List<PerfilResponse> getPerfilesByEmail(String email) {
		return PerfilSearchService.perfilListToPerfilResponseList(
				crudService.idListToPerfilList(searchDAO.getPerfilesByEmail(email)));
	}

	@Override
	public List<PerfilResponse> getPerfilesByNacimiento(String nacimiento) {
		return PerfilSearchService.perfilListToPerfilResponseList
				(crudService.idListToPerfilList(searchDAO.getPerfilesByNacimiento(nacimiento)));
	}

	@Override
	public List<PerfilResponse> getPerfilesByPrompt(String prompt) {
		prompt = prompt.replace("-", " ");
		
		return PerfilSearchService.perfilListToPerfilResponseList(
				crudService.idListToPerfilList(searchDAO.getPerfilesByPrompt(prompt)));
	}
	
	@Override
	public List<PerfilResponse> getPerfilesByRole(Roles roles){
		return PerfilSearchService.perfilListToPerfilResponseList(
				crudService.idListToPerfilList(searchDAO.getPerfilesByRole(roles)));
	}
	
	@Override
	public List<PerfilResponse> getPerfilesByMultipleParams(String query, String nombre, String email, LocalDate fromNacimiento, LocalDate toNacimiento, Roles roles) {
		List<Set<PerfilResponse>> perfilSet = new ArrayList<>();
		
		if(query != null) perfilSet.add(new HashSet<>(getPerfilesByPrompt(query)));
		
		if(nombre != null) perfilSet.add(new HashSet<>(getPerfilesByNombre(nombre)));
		
		if(email != null) perfilSet.add(new HashSet<>(getPerfilesByEmail(email)));
		
		if(fromNacimiento != null || toNacimiento != null) perfilSet.add(new HashSet<>(getPerfilesBetweenTwoBirthDates(fromNacimiento, toNacimiento)));
		
		if(roles != null) perfilSet.add(new HashSet<>(getPerfilesByRole(roles)));
		
		return intersection(perfilSet).stream().toList();
	}
	
	public List<PerfilResponse> getPerfilesBetweenTwoBirthDates(LocalDate from, LocalDate to) {
		if(from == null) from = LocalDate.MIN;
		if(to == null) to = LocalDate.of(9999, 12, 31);
		
		return PerfilSearchService.perfilListToPerfilResponseList(
				crudService.idListToPerfilList(searchDAO.getPerfilesBetweenTwoBirthDates(from, to)));
	}
	
}
