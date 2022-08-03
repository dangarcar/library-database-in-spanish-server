package es.library.databaseserver.perfil.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.service.PerfilService;

@RequestMapping("/perfiles")
@RestController
public class PerfilController {

	@Autowired
	private PerfilService perfilService;
	
	@GetMapping
	public List<Perfil> getAllPerfiles(){
		return perfilService.getAllPerfiles();
	}
	
	@GetMapping(path = "{id}")
	public Perfil getPerfilByID(@PathVariable(name = "id") Long ID) {
		return perfilService.getPerfilByID(ID);
	}
	
	@PostMapping
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
	
}
