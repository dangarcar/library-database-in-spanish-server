package es.library.databaseserver.contenido.search.service;

import java.util.List;
import java.util.stream.Collectors;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.search.AbstractContenido;
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
	
	public List<Contenido> getContenidosMasPrestados(int nContenidos);
	
	public static List<Contenido> filterContenidosByDisponibilidad(List<Contenido> conts, Boolean disponibles) {
		if(disponibles == null) return conts;
		
		return conts.stream()
				.filter(c -> c.getDisponible() == disponibles)
				.collect(Collectors.toList());
	}

	public static List<Contenido> filterContenidosByPrestable(List<Contenido> conts, Boolean prestable) {
		if(prestable == null) return conts;
		
		return conts.stream()
				.filter(c -> c.getPrestable() == prestable)
				.collect(Collectors.toList());
	}
	
	public static List<Contenido> filterContenidosByDisponibilidadAndPrestable(List<Contenido> conts, Boolean disponibles,
			Boolean prestables) {
		if(prestables == null && disponibles == null) return conts;
		
		return filterContenidosByPrestable(filterContenidosByDisponibilidad(conts, disponibles),prestables);
	}
	
	public static List<ContenidoModel> getUniqueContenidos(List<Contenido> conts) {
		return conts.stream()
				.collect(Collectors.groupingBy(ContenidoModel::ofContenido,Collectors.mapping(Contenido::getID, Collectors.toList())))
				.entrySet().stream()
				.map(entry -> {
					var c = entry.getKey();
					c.setIds(entry.getValue());
					return c;
				})
				.toList();
	}
	
	public List<? extends AbstractContenido> getContenidosByMultipleParams(String titulo, String autor, Integer ano, String idioma,
			Soporte soporte, Integer paginas, String editorial, String isbn, Integer edad, Double duracion,
			Integer calidad, String type, Boolean d, Boolean unique, Boolean prestable);
}
