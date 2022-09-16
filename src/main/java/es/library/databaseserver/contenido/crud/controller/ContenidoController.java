package es.library.databaseserver.contenido.crud.controller;

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

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.crud.service.ContenidoService;

@RequestMapping("/contenidos")
@RestController
public class ContenidoController {
	
	@Autowired
	private ContenidoService contenidoService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Contenido insertContenido(@RequestBody Contenido contenido){
		return contenidoService.insertContenido(contenido);
	}
	
	@DeleteMapping(path = "/{id}")
	public void deleteContenidoByID(@PathVariable(name = "id") Long ID) {
		contenidoService.deleteContenidoByID(ID);
	}
	
	@PutMapping(path = "/{id}")
	public Contenido updateContenidoByID(@PathVariable(name = "id") Long ID, @RequestBody Contenido contenido) {
		return contenidoService.updateContenidoByID(ID, contenido);
	}
}
