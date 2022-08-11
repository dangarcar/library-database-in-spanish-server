package es.library.databaseserver.contenido.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.search.AbstractContenido;
import es.library.databaseserver.contenido.search.ContenidoModel;
import es.library.databaseserver.contenido.search.service.ContenidoSearchService;

@RequestMapping("/contenidos/search")
@RestController
public class ContenidoSearchController {
	
	@Autowired
	private ContenidoSearchService searchService;

	@GetMapping
	public List<? extends AbstractContenido> getContenidosByParams(
			@RequestParam(required = false) String titulo,
			@RequestParam(required = false) String autor,
			@RequestParam(required = false) Integer ano,
			@RequestParam(required = false) String idioma,
			@RequestParam(required = false,name = "soporte") String soporteS,
			@RequestParam(required = false) Integer paginas,
			@RequestParam(required = false) String editorial,
			@RequestParam(required = false) String isbn,
			@RequestParam(required = false) Integer edad,
			@RequestParam(required = false) Double duracion,
			@RequestParam(required = false) Integer calidad,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) Boolean d,
			@RequestParam(required = false) Boolean p,
			@RequestParam(defaultValue = "false") Boolean unique
			){
		if (titulo == null && autor == null && ano == null && idioma == null && soporteS == null
				&& paginas == null && editorial == null && isbn == null && edad == null && duracion == null
				&& calidad == null && type == null) {
			if (unique!=null) {
				if(unique.booleanValue()) return ContenidoSearchService.getUniqueContenidos(searchService.getAllContenidos());
			}
			return ContenidoSearchService.filterContenidosByDisponibilidadAndPrestable(searchService.getAllContenidos(), d, p);
		}
		
		return searchService.getContenidosByMultipleParams(
				titulo, 
				autor, 
				ano, 
				idioma, 
				soporteS!=null? Soporte.valueOf(soporteS):null, 
				paginas, 
				editorial, 
				isbn, 
				edad, 
				duracion, 
				calidad, 
				type, 
				d, 
				unique,
				p
			);
	}
	
	@GetMapping(path = "/topprestamos")
	public List<Contenido> getContenidosMasPrestados(@RequestParam(defaultValue = "10", name = "limit") int nContenidos) {
		return searchService.getContenidosMasPrestados(nContenidos);
	}
	
	@GetMapping(path = "{word}")
	public List<ContenidoModel> getContenidoModelsByPrompt(@PathVariable(name = "word") String prompt){
		return searchService.getContenidoModelsByPrompt(prompt);
	}
	
	@GetMapping(path = "/id/{id}")
	public Contenido getContenidoByID(@PathVariable(name = "id") Long ID) {
		return searchService.getContenidoById(ID);
	}
	
	@GetMapping(path = "/type/{type}")
	public List<Contenido> getContenidosByType(@PathVariable(name = "type") String type, @RequestParam(required = false) Boolean d){
		var conts = searchService.getContenidosByType(type);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/titulo/{titulo}")
	public List<Contenido> getContenidosByTitulo(@PathVariable(name = "titulo") String titulo, @RequestParam(required = false) Boolean d) {
		titulo = titulo.replace("-", " ");
		
		var conts = searchService.getContenidosByTitulo(titulo);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/autor/{autor}")
	public List<Contenido> getContenidosByAutor(@PathVariable(name = "autor") String autor, @RequestParam(required = false) Boolean d) {
		autor = autor.replace("-", " ");
		
		var conts = searchService.getContenidosByAutor(autor);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/ano/{ano}")
	public List<Contenido> getContenidosByAno(@PathVariable(name = "ano") Integer ano, @RequestParam(required = false) Boolean d) {
		var conts = searchService.getContenidosByAno(ano);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/idioma/{idioma}")
	public List<Contenido> getContenidosByIdioma(@PathVariable(name = "idioma") String idioma, @RequestParam(required = false) Boolean d) {
		idioma = idioma.replace("-", " ");
		
		var conts = searchService.getContenidosByIdioma(idioma);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/soporte/{soporte}")
	public List<Contenido> getContenidosBySoporte(@PathVariable(name = "soporte") String soporteS, @RequestParam(required = false) Boolean d) {
		Soporte soporte = null;
		
		try{
			soporte = Soporte.valueOf(soporteS.toUpperCase());
		} catch(Exception e) {
			throw new NotValidSoporteException("En la request no se ha podido identificar ningun soporte correcto",e);
		}
		
		var conts = searchService.getContenidosBySoporte(soporte);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	
	//Detalles Libro
	
	@GetMapping(path = "/paginas/{paginas}")
	public List<Contenido> getContenidosByPaginas(@PathVariable(name = "paginas") Integer paginas, @RequestParam(required = false) Boolean d) {
		var conts = searchService.getContenidosByPaginas(paginas);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/editorial/{editorial}")
	public List<Contenido> getContenidosByEditorial(@PathVariable(name = "editorial") String editorial, @RequestParam(required = false) Boolean d) {
		editorial = editorial.replace("-", " ");
		
		var conts = searchService.getContenidosByEditorial(editorial);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/isbn/{isbn}")
	public List<Contenido> getContenidosByISBN(@PathVariable(name = "isbn") String isbn, @RequestParam(required = false) Boolean d) {
		var conts = searchService.getContenidosByISBN(isbn);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	
	//Detalles audiovisual
	
	@GetMapping(path = "/edad/{edad}")
	public List<Contenido> getContenidosByEdadRecomendada(@PathVariable(name = "edad") Integer edad, @RequestParam(required = false) Boolean d) {
		var conts = searchService.getContenidosByEdadRecomendada(edad);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/duracion/{duracion}")
	public List<Contenido> getContenidosByDuracion(@PathVariable(name = "duracion") Double duracion, @RequestParam(required = false) Boolean d) {
		var conts = searchService.getContenidosByDuracion(duracion);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
	
	@GetMapping(path = "/calidad/{calidad}")
	public List<Contenido> getContenidosByCalidad(@PathVariable(name = "calidad") Integer calidad, @RequestParam(required = false) Boolean d) {
		var conts = searchService.getContenidosByCalidad(calidad);
		
		return ContenidoSearchService.filterContenidosByDisponibilidad(conts, d);
	}
}
