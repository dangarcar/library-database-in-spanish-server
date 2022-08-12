package es.library.databaseserver.contenido.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.contenido.crud.model.DetallesLibroModel;
import es.library.databaseserver.contenido.crud.service.DetallesLibroService;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;

@RequestMapping("/libros")
@RestController
public class LibrosController {

	@Autowired
	private DetallesLibroService dLibroService;
	
	@GetMapping
	public List<DetallesLibroModel> getAllLibro(){return dLibroService.getAllLibro();}
	
	@GetMapping(path = "{id}")
	public DetallesLibroModel getLibroByID(@PathVariable(value = "id") Long ID) 
			throws ContenidoNotFoundException{return dLibroService.getLibroByID(ID);}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public DetallesLibroModel insertLibro(@RequestBody DetallesLibroModel libro) 
			throws DatabaseContenidoException{return dLibroService.insertLibro(libro);}
	
	@DeleteMapping(path = "{id}")
	public void deleteLibroByID(@PathVariable(value = "id") Long ID) 
			throws ContenidoNotFoundException{dLibroService.deleteLibroByID(ID);}
	
	@PutMapping(path = "{id}")	
	public DetallesLibroModel updateLibroByID(@PathVariable(value = "id") Long ID, @RequestBody DetallesLibroModel libro)  
			throws ContenidoNotFoundException, DatabaseContenidoException{return dLibroService.updateLibroByID(ID, libro);}
	
}