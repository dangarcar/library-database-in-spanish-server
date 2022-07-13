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

import es.library.databaseserver.contenido.exceptions.ContenidoNotInsertedException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.service.ContenidoService;

@RequestMapping("/audiovisual")
@RestController
public class AudiovisualController {

	@Autowired
	private ContenidoService contenidoService;
	
	@GetMapping
	public List<DetallesAudiovisualModel> getAllAudiovisual(){return contenidoService.getAllAudiovisual();}
	
	@GetMapping(path = "{id}")
	public DetallesAudiovisualModel getAudiovisualByID(@PathVariable(value = "id") Long ID) 
			throws NoSuchContenidoException{return contenidoService.getAudiovisualByID(ID);}
	
	@PostMapping
	public void insertAudiovisual(@RequestBody DetallesAudiovisualModel audiovisual) 
			throws ContenidoNotInsertedException{contenidoService.insertAudiovisual(audiovisual);}
	
	@DeleteMapping(path = "{id}")
	public void deleteAudiovisualByID(@PathVariable(value = "id") Long ID)  
			throws NoSuchContenidoException{contenidoService.deleteAudiovisualByID(ID);}
	
	@PutMapping(path = "{id}")		
	public void updateAudiovisualByID(@PathVariable(value = "id") Long ID, @RequestBody DetallesAudiovisualModel audiovisual)  
			throws NoSuchContenidoException{contenidoService.updateAudiovisualByID(ID, audiovisual);}
	
}
