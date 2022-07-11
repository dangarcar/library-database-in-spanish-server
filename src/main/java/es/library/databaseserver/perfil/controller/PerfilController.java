package es.library.databaseserver.perfil.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.service.PerfilService;

@RequestMapping("/perfiles")
@RestController
public class PerfilController {
	
	private final PerfilService perfilService;
	
	@Autowired
	public PerfilController(PerfilService perfilService) {
		this.perfilService = perfilService;
	}
	
	@GetMapping
	public List<Perfil> getAllPerfiles() throws SQLException {
		return perfilService.getAllPerfiles();
	}
	
	@GetMapping(path = "{dni}")
	public Perfil getPerfilByDNI(@PathVariable(name = "dni") int DNI) throws SQLException {
		return perfilService.getPerfilByDNI(DNI);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void insertPerfil(@Validated@RequestBody Perfil perfil) throws SQLException {
		perfilService.insertPerfil(perfil);
	}
	
	@DeleteMapping(path = "{dni}")
	public void deletePerfilByDNI(@PathVariable(name = "dni") int DNI) throws SQLException {
		perfilService.deletePerfilByDNI(DNI);
	}
	
	@PutMapping(path = "{dni}")
	public void updatePerfilByDNI(@PathVariable(name = "dni") int DNI, @Validated@RequestBody Perfil perfil) throws SQLException {
		perfilService.updatePerfilByDNI(DNI, perfil);
	}
}
