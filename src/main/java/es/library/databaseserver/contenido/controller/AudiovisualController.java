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

import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.service.DetallesAudiovisualService;

@RequestMapping("/audiovisual")
@RestController
public class AudiovisualController {

	@Autowired
	private DetallesAudiovisualService dAudiovisualService;
	
	@GetMapping
	public List<DetallesAudiovisualModel> getAllAudiovisual(){return dAudiovisualService.getAllAudiovisual();}
	
	@GetMapping(path = "{id}")
	public DetallesAudiovisualModel getAudiovisualByID(@PathVariable(value = "id") Long ID) 
			throws ContenidoNotFoundException{return dAudiovisualService.getAudiovisualByID(ID);}
	
	@PostMapping
	public DetallesAudiovisualModel insertAudiovisual(@RequestBody DetallesAudiovisualModel audiovisual) 
			throws DatabaseContenidoException{return dAudiovisualService.insertAudiovisual(audiovisual);}
	
	@DeleteMapping(path = "{id}")
	public void deleteAudiovisualByID(@PathVariable(value = "id") Long ID)  
			throws ContenidoNotFoundException{dAudiovisualService.deleteAudiovisualByID(ID);}
	
	@PutMapping(path = "{id}")		
	public DetallesAudiovisualModel updateAudiovisualByID(@PathVariable(value = "id") Long ID, @RequestBody DetallesAudiovisualModel audiovisual)  
			throws ContenidoNotFoundException, DatabaseContenidoException{return dAudiovisualService.updateAudiovisualByID(ID, audiovisual);}
	
}
