package es.library.databaseserver.perfil.search.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.model.PerfilResponse;
import es.library.databaseserver.perfil.search.service.PerfilSearchService;

@RequestMapping("/perfiles/search")
@RestController
public class PerfilSearchController {

	@Autowired
	private PerfilSearchService perfilService;
	
	@GetMapping(path = "/query/{prompt}")
	public List<PerfilResponse> getPerfilesByPrompt(@PathVariable(name = "prompt") String prompt) {		
		return perfilService.getPerfilesByPrompt(prompt);
	}
	
	@GetMapping(path = "/id/{id}")
	public PerfilResponse getPerfilByID(@PathVariable(name = "id") Long ID) {
		return perfilService.getPerfilByID(ID);
	}
	
	@GetMapping(path = "/username/{username}")
	public PerfilResponse getPerfilByUsername(String username){
		return perfilService.getPerfilByUsername(username);
	}
	
	@GetMapping(path = "/nombre/{nombre}")
	public List<PerfilResponse> getPerfilesByNombre(@PathVariable(name = "nombre") String nombre){
		nombre = nombre.replace("-", " ");
		
		return perfilService.getPerfilesByNombre(nombre);
	}
	
	@GetMapping(path = "/email/{email}")
	public List<PerfilResponse> getPerfilesByEmail(@PathVariable(name = "email") String email){
		return perfilService.getPerfilesByEmail(email);
	}
	
	@GetMapping(path = "/nacimiento/{nacimiento}")
	public List<PerfilResponse> getPerfilesByNacimiento(@PathVariable(name = "nacimiento") String nacimiento) {
		return perfilService.getPerfilesByNacimiento(nacimiento);
	}
	
	@GetMapping(path = "/role/{role}")
	public List<PerfilResponse> getPerfilesByRole(@PathVariable(value = "role") String role) {
		return perfilService.getPerfilesByRole(role!=null? Roles.valueOf(role):null);
	}

	@GetMapping
	public List<PerfilResponse> getPerfilByParams(
			@RequestParam(required = false, name = "q") String query,
			@RequestParam(required = false) String nombre, 
			@RequestParam(required = false) String email, 
			@RequestParam(required = false) String fromNacimiento, 
			@RequestParam(required = false) String toNacimiento, 
			@RequestParam(required = false) String role) {
		if(nombre==null && email==null && fromNacimiento==null && toNacimiento==null && query==null && role == null) {
			return perfilService.getAllPerfiles();
		}
		
		return perfilService.getPerfilesByMultipleParams(
				query,
				nombre, 
				email, 
				fromNacimiento!=null? LocalDate.parse(fromNacimiento):null, 
				toNacimiento!=null? LocalDate.parse(toNacimiento):null, 
				role!=null? Roles.valueOf(role):null);
	}
	
}
