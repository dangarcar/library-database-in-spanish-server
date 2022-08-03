package es.library.databaseserver.contenido.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.search.service.ContenidoSearchService;

@RequestMapping("/contenidos/search")
@RestController
public class ContenidoSearchController {
	
	@Autowired
	private ContenidoSearchService searchService;
	
	@GetMapping
	public List<Contenido> getAllContenidos(){
		return searchService.getAllContenidos();
	}
	
	@GetMapping(path = "/id/{id}")
	public Contenido getContenidoByID(@PathVariable(name = "id") Long ID) {
		return searchService.getContenidoById(ID);
	}
	
	@GetMapping(path = "/titulo/{titulo}")
	public List<Contenido> getContenidosByTitulo(@PathVariable(name = "titulo") String titulo) {
		titulo = titulo.replace("-", " ");
		
		return searchService.getContenidosByTitulo(titulo);
	}
	
	@GetMapping(path = "/autor/{autor}")
	public List<Contenido> getContenidosByAutor(@PathVariable(name = "autor") String autor) {
		autor = autor.replace("-", " ");
		
		return searchService.getContenidosByAutor(autor);
	}
	
	@GetMapping(path = "/ano/{ano}")
	public List<Contenido> getContenidosIDByAno(@PathVariable(name = "ano") Integer ano) {
		return searchService.getContenidosIDByAno(ano);
	}
	
	@GetMapping(path = "/idioma/{idioma}")
	public List<Contenido> getContenidosIDByIdioma(@PathVariable(name = "idioma") String idioma) {
		idioma = idioma.replace("-", " ");
		
		return searchService.getContenidosIDByIdioma(idioma);
	}
	
	@GetMapping(path = "/soporte/{soporte}")
	public List<Contenido> getContenidosIDBySoporte(@PathVariable(name = "soporte") String soporteS) {
		var soporte = Soporte.valueOf(soporteS.toUpperCase());
		
		return searchService.getContenidosIDBySoporte(soporte);
	}
}
