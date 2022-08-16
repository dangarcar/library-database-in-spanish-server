package es.library.databaseserver.perfil.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.service.PerfilService;

@RequestMapping("/perfiles")
@RestController
public class PerfilController {

	@Autowired
	private PerfilService perfilService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Perfil insertPerfil(@RequestBody Perfil perfil) {
		return perfilService.insertPerfil(perfil);
	}
	
	@DeleteMapping(path = "{id}")
	public void deletePerfilByID(@PathVariable(name = "id") Long ID) {
		perfilService.deletePerfilByID(ID);
	}
	
	@PutMapping(path = "{id}")
	public Perfil updatePerfilByID(@PathVariable(name = "id") Long ID, @RequestBody Perfil perfil) {
		return perfilService.updatePerfilByID(ID, perfil);
	}
	
	@PutMapping(path = "/admin/{id}")
	public void makePerfilAdmin(@PathVariable(name = "id") Long id) {
		perfilService.makePerfilAdmin(id);
	}
	
	@PutMapping(path = "/perfil/{id}")
	public void makeAdminPerfil(@PathVariable(name = "id") Long id) {
		perfilService.makeAdminPerfil(id);
	}
	
}
