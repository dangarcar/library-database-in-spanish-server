package es.library.databaseserver.contenido.controller;

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
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.service.ContenidoService;

@RequestMapping("/contenidos")
@RestController
public class ContenidoController {
	
	@Autowired
	private ContenidoService contenidoService;
	
	@GetMapping
	public List<Contenido> getAllContenidos(){
		return contenidoService.getAllContenidos();
	}
	
	@GetMapping(path = "{id}")
	public Contenido getContenidoByID(@PathVariable(name = "id") Long ID) throws NoSuchContenidoException, NotValidTypeContenidoException, NotValidSoporteException{
		return contenidoService.getContenidoByID(ID);
	}
	
	@PostMapping
	public void insertContenido(@RequestBody Contenido contenido) throws NotInsertedContenidoException{
		contenidoService.insertContenido(contenido);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteContenidoByID(@PathVariable(name = "id") Long ID) throws NoSuchContenidoException{
		contenidoService.deleteContenidoByID(ID);
	}
	
	@PutMapping(path = "{id}")
	public void updateContenidoByID(@PathVariable(name = "id") Long ID, @RequestBody Contenido contenido) throws NoSuchContenidoException{
		contenidoService.updateContenidoByID(ID, contenido);
	}
}
