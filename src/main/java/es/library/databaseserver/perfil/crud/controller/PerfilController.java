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
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.model.PerfilResponse;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.perfil.exceptions.IllegalPerfilException;

@RequestMapping("/perfiles")
@RestController
public class PerfilController {

	@Autowired
	private PerfilService perfilService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PerfilResponse insertPerfil(@RequestBody Perfil perfil) {		
		return new PerfilResponse(perfilService.insertPerfil(perfil));
	}
	
	@DeleteMapping(path = "{id}")
	public void deletePerfilByID(@PathVariable(name = "id") Long ID) {
		perfilService.deletePerfilByID(ID);
	}
	
	@PutMapping(path = "{id}")
	public PerfilResponse updatePerfilByID(@PathVariable(name = "id") Long ID, @RequestBody Perfil perfil) {
		return new PerfilResponse(perfilService.updatePerfilByID(ID, perfil));
	}
	
	@PutMapping(path = "/roles/{id}/{role}")
	public void setRole(@PathVariable(name = "id") long id, @PathVariable(name = "role") String roles) {
		Roles roles2;
		
		try {
			 roles2 = Roles.valueOf("ROLE_"+roles.toUpperCase());
		} catch (Exception e) {
			throw new IllegalPerfilException(e.getMessage(), e);
		}
		
		perfilService.setRole(id, roles2);
	}
	
}
