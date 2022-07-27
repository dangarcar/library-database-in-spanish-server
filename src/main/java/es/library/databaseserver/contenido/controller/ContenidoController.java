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
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.service.ContenidoService;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;

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
	public Contenido getContenidoByID(@PathVariable(name = "id") Long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException{
		return contenidoService.getContenidoByID(ID);
	}
	
	@PostMapping
	public Contenido insertContenido(@RequestBody Contenido contenido) throws DatabaseContenidoException, NotValidTypeContenidoException, NotValidSoporteException, ContenidoAlreadyExistsException{
		return contenidoService.insertContenido(contenido);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteContenidoByID(@PathVariable(name = "id") Long ID) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException {
		contenidoService.deleteContenidoByID(ID);
	}
	
	@PutMapping(path = "{id}")
	public Contenido updateContenidoByID(@PathVariable(name = "id") Long ID, @RequestBody Contenido contenido) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException, DatabaseContenidoException, ContenidoAlreadyExistsException{
		return contenidoService.updateContenidoByID(ID, contenido);
	}
}
