package es.library.databaseserver.contenido.search.service.implementations;

import static es.library.databaseserver.utils.Utils.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.crud.Audio;
import es.library.databaseserver.contenido.crud.Libros;
import es.library.databaseserver.contenido.crud.Videos;
import es.library.databaseserver.contenido.crud.service.ContenidoService;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.search.AbstractContenido;
import es.library.databaseserver.contenido.search.ContenidoModel;
import es.library.databaseserver.contenido.search.dao.ContenidoSearchDAO;
import es.library.databaseserver.contenido.search.service.ContenidoSearchService;

@Service
public class ContenidoSearchServiceImpl implements ContenidoSearchService {

	@Autowired
	private ContenidoService contenidoCRUDService;
	
	@Autowired
	private ContenidoSearchDAO contenidoSearchDAO;
	
	@Override
	public List<Contenido> getAllContenidos() {
		return contenidoCRUDService.getAllContenidos();
	}

	@Override
	public List<ContenidoModel> getContenidoModelsByPrompt(String prompt) {
		return getUniqueContenidos(contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByPrompt(prompt)));
	}
	
	@Override
	public Contenido getContenidoById(Long id) throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException {
		return contenidoCRUDService.getContenidoByID(id);
	}

	@Override
	public List<Contenido> getContenidosByTitulo(String titulo) throws ContenidoNotFoundException {		
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByTitulo(titulo));
	}

	@Override
	public List<Contenido> getContenidosByAutor(String autor) throws ContenidoNotFoundException {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByAutor(autor));
	}

	@Override
	public List<Contenido> getContenidosByAno(Integer ano) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByAno(ano));
	}

	@Override
	public List<Contenido> getContenidosByIdioma(String idioma) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByIdioma(idioma));
	}

	@Override
	public List<Contenido> getContenidosBySoporte(Soporte soporte) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDBySoporte(soporte));
	}

	@Override
	public List<Contenido> getContenidosByPaginas(Integer paginas) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByPaginas(paginas));
	}

	@Override
	public List<Contenido> getContenidosByEditorial(String editorial) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByEditorial(editorial));
	}

	@Override
	public List<Contenido> getContenidosByISBN(String isbn) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByISBN(isbn));
	}

	@Override
	public List<Contenido> getContenidosByEdadRecomendada(Integer edad) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByEdadRecomendada(edad));
	}

	@Override
	public List<Contenido> getContenidosByDuracion(Double duracion) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByDuracion(duracion));
	}

	@Override
	public List<Contenido> getContenidosByCalidad(Integer calidad) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByCalidad(calidad));
	}

	@Override
	public List<Contenido> getContenidosByType(String typeName) {
		Map<String, Class<?>> map = Map.of("audio",Audio.class,
				"libro",Libros.class,
				"video",Videos.class);
		
		return contenidoCRUDService.getAllContenidos().stream()
				.filter(c -> c.getClass().equals(map.get(typeName)))
				.toList();
	}

	public List<Contenido> filterContenidosByDisponibilidad(List<Contenido> conts, Boolean disponibles) {
		if(disponibles == null) return conts;
		
		return conts.stream()
				.filter(c -> c.getDisponible() == disponibles)
				.collect(Collectors.toList());
	}
	
	public List<ContenidoModel> getUniqueContenidos(List<Contenido> conts) {
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

	@Override
	public List<? extends AbstractContenido> getContenidosByMultipleParams(String titulo, String autor, Integer ano, String idioma,
			Soporte soporte, Integer paginas, String editorial, String isbn, Integer edad, Double duracion,
			Integer calidad, String type, Boolean d, Boolean unique, Boolean prestable) {
		List<Set<Contenido>> contenidoSets = new ArrayList<>();
		
		
		
		if(titulo != null)    contenidoSets.add(new HashSet<>(getContenidosByTitulo(titulo))); 
		
		if(autor != null)     contenidoSets.add(new HashSet<>(getContenidosByAutor(autor)));
		
		if(ano != null)       contenidoSets.add(new HashSet<>(getContenidosByAno(ano)));
		
		if(idioma != null)    contenidoSets.add(new HashSet<>(getContenidosByIdioma(idioma))); 
		
		if(soporte != null)   contenidoSets.add(new HashSet<>(getContenidosBySoporte(soporte)));
		
		if(paginas != null)   contenidoSets.add(new HashSet<>(getContenidosByPaginas(paginas)));
		
		if(editorial != null) contenidoSets.add(new HashSet<>(getContenidosByEditorial(editorial)));
		
		if(isbn != null)      contenidoSets.add(new HashSet<>(getContenidosByISBN(isbn)));
		
		if(edad != null)      contenidoSets.add(new HashSet<>(getContenidosByEdadRecomendada(edad)));
		
		if(duracion != null)  contenidoSets.add(new HashSet<>(getContenidosByDuracion(duracion)));
		 
		if(calidad != null)   contenidoSets.add(new HashSet<>(getContenidosByCalidad(calidad)));
		
		if(type != null)      contenidoSets.add(new HashSet<>(getContenidosByType(type)));
		
		
		
		var contenidoList = intersection(contenidoSets).stream().toList();
		
		if(unique) {
			return getUniqueContenidos(contenidoList);
		}
		return filterContenidosByDisponibilidadAndPrestable(contenidoList, d,prestable);
	}

	@Override
	public List<Contenido> filterContenidosByPrestable(List<Contenido> conts, Boolean prestable) {
		if(prestable == null) return conts;
		
		return conts.stream()
				.filter(c -> c.getPrestable() == prestable)
				.collect(Collectors.toList());
	}

	@Override
	public List<Contenido> filterContenidosByDisponibilidadAndPrestable(List<Contenido> conts, Boolean disponibles,
			Boolean prestables) {
		if(prestables == null && disponibles == null) return conts;
		
		return filterContenidosByPrestable(filterContenidosByDisponibilidad(conts, disponibles),prestables);
	}

}
