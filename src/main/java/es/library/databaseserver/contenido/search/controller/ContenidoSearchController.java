package es.library.databaseserver.contenido.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.search.service.ContenidoSearchService;
import es.library.databaseserver.contenido.types.ContenidoModel;

@RequestMapping("/contenidos/search")
@RestController
public class ContenidoSearchController {
	
	@Autowired
	private ContenidoSearchService searchService;

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/unique")
	public List<ContenidoModel> getContenidosByParams(
			@RequestParam(required = false, name = "q") String query, 
			@RequestParam(required = false) String titulo, 
			@RequestParam(required = false) String autor,
			@RequestParam(required = false) Integer minAno, 
			@RequestParam(required = false) Integer maxAno, 
			@RequestParam(required = false) String idioma, 
			@RequestParam(required = false) String soporte, 
			@RequestParam(required = false) Integer minPaginas, 
			@RequestParam(required = false) Integer maxPaginas,
			@RequestParam(required = false) String editorial, 
			@RequestParam(required = false) String isbn, 
			@RequestParam(required = false) Integer minEdad, 
			@RequestParam(required = false) Integer maxEdad, 
			@RequestParam(required = false) Double minDuracion, 
			@RequestParam(required = false) Double maxDuracion,
			@RequestParam(required = false) Integer minCalidad, 
			@RequestParam(required = false) Integer maxCalidad, 
			@RequestParam(required = false) String type){
		if (query == null && titulo == null && autor == null && minAno == null && maxAno == null && idioma == null
				&& soporte == null && minPaginas == null && maxPaginas == null && editorial == null && isbn == null
				&& minEdad == null && maxEdad == null && minDuracion == null && maxDuracion == null
				&& minCalidad == null && maxCalidad == null && type == null) {
			return ContenidoSearchService.getUniqueContenidos(searchService.getAllContenidos());
		}

		List<ContenidoModel> contenidos = (List<ContenidoModel>) searchService.getContenidosByMultipleParams(
				query, 
				titulo, 
				autor,
				minAno, 
				maxAno, 
				idioma, 
				soporte!=null? Soporte.valueOf(soporte):null, 
				minPaginas, 
				maxPaginas,
				editorial, 
				isbn, 
				minEdad, 
				maxEdad, 
				minDuracion, 
				maxDuracion,
				minCalidad, 
				maxCalidad, 
				type, 
				null, 
				true,
				null
			);
		
		if (contenidos.isEmpty()) throw new ContenidoNotFoundException("Ningún contenido coincide con las condiciones propuestas");
		
		return contenidos;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping
	public List<Contenido> getContenidosByParams(
			@RequestParam(required = false, name = "q") String query, 
			@RequestParam(required = false) String titulo, 
			@RequestParam(required = false) String autor,
			@RequestParam(required = false) Integer minAno, 
			@RequestParam(required = false) Integer maxAno, 
			@RequestParam(required = false) String idioma, 
			@RequestParam(required = false) String soporte, 
			@RequestParam(required = false) Integer minPaginas, 
			@RequestParam(required = false) Integer maxPaginas,
			@RequestParam(required = false) String editorial, 
			@RequestParam(required = false) String isbn, 
			@RequestParam(required = false) Integer minEdad, 
			@RequestParam(required = false) Integer maxEdad, 
			@RequestParam(required = false) Double minDuracion, 
			@RequestParam(required = false) Double maxDuracion,
			@RequestParam(required = false) Integer minCalidad, 
			@RequestParam(required = false) Integer maxCalidad, 
			@RequestParam(required = false) String type, 
			@RequestParam(required = false) Boolean d, 
			@RequestParam(required = false) Boolean p){
		if (query == null && titulo == null && autor == null && minAno == null && maxAno == null && idioma == null
				&& soporte == null && minPaginas == null && maxPaginas == null && editorial == null && isbn == null
				&& minEdad == null && maxEdad == null && minDuracion == null && maxDuracion == null
				&& minCalidad == null && maxCalidad == null && type == null) {
			return ContenidoSearchService.filterContenidosByDisponibilidadAndPrestable(searchService.getAllContenidos(),
					d, p);
		}

		List<Contenido> contenidos = (List<Contenido>) searchService.getContenidosByMultipleParams(
				query, 
				titulo, 
				autor,
				minAno, 
				maxAno, 
				idioma, 
				soporte!=null? Soporte.valueOf(soporte):null, 
				minPaginas, 
				maxPaginas,
				editorial, 
				isbn, 
				minEdad, 
				maxEdad, 
				minDuracion, 
				maxDuracion,
				minCalidad, 
				maxCalidad, 
				type, 
				d, 
				false,
				p
			);
		
		if (contenidos.isEmpty()) throw new ContenidoNotFoundException("Ningún contenido coincide con las condiciones propuestas");
		
		return contenidos;
	}
	
	@GetMapping(path = "/topprestamos")
	public List<Contenido> getContenidosMasPrestados(@RequestParam(defaultValue = "10", name = "limit") int nContenidos) {
		return searchService.getContenidosMasPrestados(nContenidos);
	}
	
	@GetMapping(path = "/query/{word}")
	public List<Contenido> getContenidosByPrompt(@PathVariable(name = "word") String prompt){
		return searchService.getContenidosByPrompt(prompt);
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
		var conts = searchService.getContenidosByAno(ano,ano);
		
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
