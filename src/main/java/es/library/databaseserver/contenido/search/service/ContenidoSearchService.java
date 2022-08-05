package es.library.databaseserver.contenido.search.service;

import java.util.List;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.search.ContenidoModel;

public interface ContenidoSearchService {

	public List<Contenido> getAllContenidos();
	
	public List<ContenidoModel> getContenidoModelsByPrompt(String prompt);
	
	public Contenido getContenidoById(Long id) throws ContenidoNotFoundException;
	
	public List<Contenido> getContenidosByTitulo(String titulo);
	
	public List<Contenido> getContenidosByAutor(String autor);
	
	public List<Contenido> getContenidosByAno(Integer ano);
	
	public List<Contenido> getContenidosByIdioma(String idioma);
	
	public List<Contenido> getContenidosBySoporte(Soporte soporte);
	
	public List<Contenido> getContenidosByType(String typeName);
	
	//Detalles Libro
	
	public List<Contenido> getContenidosByPaginas(Integer paginas);
	
	public List<Contenido> getContenidosByEditorial(String editorial);
	
	public List<Contenido> getContenidosByISBN(String isbn);
	
	
	//Detalles audiovisual
	
	public List<Contenido> getContenidosByEdadRecomendada(Integer edad);
	
	public List<Contenido> getContenidosByDuracion(Double duracion);
	
	public List<Contenido> getContenidosByCalidad(Integer calidad);
	
	
	//Util methods
	
	/**
	 * Si disponibles es null, se devuelve la lista conts como tal
	 */
	public List<Contenido> filterContenidosByDisponibilidad(List<Contenido> conts, Boolean disponibles);
	
	public List<ContenidoModel> getUniqueContenidos(List<Contenido> conts);
}
