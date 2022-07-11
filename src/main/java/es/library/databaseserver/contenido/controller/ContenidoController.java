package es.library.databaseserver.contenido.controller;

import java.sql.SQLException;
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

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.service.ContenidoService;

@RequestMapping("/contenidos")
@RestController
public class ContenidoController {
	
	@Autowired
	private final ContenidoService contenidoService;
	
	public ContenidoController(ContenidoService contenidoService) {
		this.contenidoService = contenidoService;
	}
	
	@GetMapping
	public List<Contenido> getAllContenidos() throws SQLException {
		return contenidoService.getAllContenidos();
	}
	
	@GetMapping(path = "{id}")
	public Contenido getContenidoByDNI(@PathVariable(name = "id") Long ID) throws SQLException {
		return contenidoService.getContenidoByID(ID);
	}
	
	@PostMapping
	public void insertContenido(@RequestBody Contenido contenido) throws SQLException {
		contenidoService.insertContenido(contenido);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteContenidoByDNI(@PathVariable(name = "id") Long ID) throws SQLException {
		contenidoService.deleteContenidoByID(ID);
	}
	
	@PutMapping(path = "{id}")
	public void updateContenidoByDNI(@PathVariable(name = "id") Long ID, @RequestBody Contenido contenido) throws SQLException {
		contenidoService.updateContenidoByID(ID, contenido);
	}
}
