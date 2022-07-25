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

import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.DetallesLibroModel;
import es.library.databaseserver.contenido.service.implementations.ContenidoServiceImpl;

@RequestMapping("/libros")
@RestController
public class LibrosController {

	@Autowired
	private ContenidoServiceImpl contenidoServiceImpl;
	
	@GetMapping
	public List<DetallesLibroModel> getAllLibro(){return contenidoServiceImpl.getAllLibro();}
	
	@GetMapping(path = "{id}")
	public DetallesLibroModel getLibroByID(@PathVariable(value = "id") Long ID) 
			throws NoSuchContenidoException{return contenidoServiceImpl.getLibroByID(ID);}
	
	@PostMapping
	public void insertLibro(@RequestBody DetallesLibroModel libro) 
			throws NotInsertedContenidoException{contenidoServiceImpl.insertLibro(libro);}
	
	@DeleteMapping(path = "{id}")
	public void deleteLibroByID(@PathVariable(value = "id") Long ID) 
			throws NoSuchContenidoException{contenidoServiceImpl.deleteLibroByID(ID);}
	
	@PutMapping(path = "{id}")	
	public void updateLibroByID(@PathVariable(value = "id") Long ID, @RequestBody DetallesLibroModel libro)  
			throws NoSuchContenidoException, NotInsertedContenidoException{contenidoServiceImpl.updateLibroByID(ID, libro);}
	
}