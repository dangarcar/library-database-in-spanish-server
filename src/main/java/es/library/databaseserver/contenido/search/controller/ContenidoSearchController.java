package es.library.databaseserver.contenido.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
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
	
	@GetMapping(path = "{word}")
	public List<Contenido> getContenidosByPrompt(@PathVariable(name = "word") String prompt){
		return searchService.getContenidosByPrompt(prompt);
	}
	
	@GetMapping(path = "/type/{type}")
	public List<Contenido> getContenidosByType(@PathVariable(name = "type") String type){
		return searchService.getContenidosByType(type);
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
	public List<Contenido> getContenidosByAno(@PathVariable(name = "ano") Integer ano) {
		return searchService.getContenidosByAno(ano);
	}
	
	@GetMapping(path = "/idioma/{idioma}")
	public List<Contenido> getContenidosByIdioma(@PathVariable(name = "idioma") String idioma) {
		idioma = idioma.replace("-", " ");
		
		return searchService.getContenidosByIdioma(idioma);
	}
	
	@GetMapping(path = "/soporte/{soporte}")
	public List<Contenido> getContenidosBySoporte(@PathVariable(name = "soporte") String soporteS) {
		Soporte soporte = null;
		
		try{
			soporte = Soporte.valueOf(soporteS.toUpperCase());
		} catch(Exception e) {
			throw new NotValidSoporteException("En la request no se ha podido identificar ningun soporte correcto",e);
		}
		
		return searchService.getContenidosBySoporte(soporte);
	}
	
	
	//Detalles Libro
	
	@GetMapping(path = "/paginas/{paginas}")
	public List<Contenido> getContenidosByPaginas(@PathVariable(name = "paginas") Integer paginas) {
		return searchService.getContenidosByPaginas(paginas);
	}
	
	@GetMapping(path = "/editorial/{editorial}")
	public List<Contenido> getContenidosByEditorial(@PathVariable(name = "editorial") String editorial) {
		editorial = editorial.replace("-", " ");
		
		return searchService.getContenidosByEditorial(editorial);
	}
	
	@GetMapping(path = "/isbn/{isbn}")
	public List<Contenido> getContenidosByISBN(@PathVariable(name = "isbn") String isbn) {
		return searchService.getContenidosByISBN(isbn);
	}
	
	
	//Detalles audiovisual
	
	@GetMapping(path = "/edad/{edad}")
	public List<Contenido> getContenidosByEdadRecomendada(@PathVariable(name = "edad") Integer edad) {
		return searchService.getContenidosByEdadRecomendada(edad);
	}
	
	@GetMapping(path = "/duracion/{duracion}")
	public List<Contenido> getContenidosByDuracion(@PathVariable(name = "duracion") Double duracion) {
		return searchService.getContenidosByDuracion(duracion);
	}
	
	@GetMapping(path = "/calidad/{calidad}")
	public List<Contenido> getContenidosByCalidad(@PathVariable(name = "calidad") Integer calidad) {
		return searchService.getContenidosByCalidad(calidad);
	}
}
