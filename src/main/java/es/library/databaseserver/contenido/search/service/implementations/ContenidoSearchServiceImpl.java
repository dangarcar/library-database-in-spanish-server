package es.library.databaseserver.contenido.search.service.implementations;

import static es.library.databaseserver.shared.Utils.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.crud.service.ContenidoService;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.search.dao.ContenidoSearchDAO;
import es.library.databaseserver.contenido.search.service.ContenidoSearchService;
import es.library.databaseserver.contenido.types.AbstractContenido;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;
import es.library.databaseserver.contenido.types.Video;
import es.library.databaseserver.prestamos.search.dao.PrestamoSearchDAO;

@Service
public class ContenidoSearchServiceImpl implements ContenidoSearchService {

	@Autowired
	private ContenidoService contenidoCRUDService;
	
	@Autowired
	private ContenidoSearchDAO contenidoSearchDAO;
	
	@Autowired
	private PrestamoSearchDAO prestamoSearchDAO;
	
	@Override
	public List<Contenido> getAllContenidos() {
		return contenidoCRUDService.getAllContenidos();
	}

	@Override
	public List<Contenido> getContenidosByPrompt(String prompt) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByPrompt(prompt));
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
	public List<Contenido> getContenidosByAno(Integer min, Integer max) {
		if(min == null) min = 0;
		if(max == null) max = Integer.MAX_VALUE;
		
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByAno(min,max));
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
	public List<Contenido> getContenidosByPaginas(Integer min, Integer max) {
		if(min == null) min = 0;
		if(max == null) max = Integer.MAX_VALUE;
		
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByPaginas(min,max));
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
	public List<Contenido> getContenidosByEdadRecomendada(Integer min, Integer max) {
		if(min == null) min = 0;
		if(max == null) max = Integer.MAX_VALUE;
		
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByEdadRecomendada(min,max));
	}
	
	@Override
	public List<Contenido> getContenidosByDuracion(Double duracion) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByDuracion(duracion));
	}
	@Override
	public List<Contenido> getContenidosByDuracion(Double min, Double max) {
		if(min == null) min = 0.0;
		if(max == null) max = Double.MAX_VALUE;
		
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByDuracion(min,max));
	}
	
	@Override
	public List<Contenido> getContenidosByCalidad(Integer calidad) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByCalidad(calidad));
	}
	@Override
	public List<Contenido> getContenidosByCalidad(Integer min, Integer max) {
		if(min == null) min = 0;
		if(max == null) max = Integer.MAX_VALUE;
		
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByCalidad(min,max));
	}
	
	public List<Contenido> getContenidosMasPrestados(int nContenidos) {
		return contenidoCRUDService.idListToContenidoList(prestamoSearchDAO.getContenidosMasPrestados(nContenidos));
	}
	
	@Override
	public List<Contenido> getContenidosByType(String typeName) {
		Map<String, Class<?>> map = Map.of("audio",Audio.class,
				"libro",Libro.class,
				"video",Video.class);
		
		return contenidoCRUDService.getAllContenidos().stream()
				.filter(c -> c.getClass().equals(map.get(typeName)))
				.toList();
	}


	@Override
	public List<? extends AbstractContenido> getContenidosByMultipleParams(String query, String titulo, String autor,
			Integer minAno, Integer maxAno, String idioma, Soporte soporte, Integer minPaginas, Integer maxPaginas,
			String editorial, String isbn, Integer minEdad, Integer maxEdad, Double minDuracion, Double maxDuracion,
			Integer minCalidad, Integer maxCalidad, String type, Boolean d, Boolean unique, Boolean prestable) {
		
		List<Set<Contenido>> contenidoSets = new ArrayList<>();
		
		
		if(query != null)     							contenidoSets.add(new HashSet<>(getContenidosByPrompt(query)));
		
		if(titulo != null)    							contenidoSets.add(new HashSet<>(getContenidosByTitulo(titulo))); 
		
		if(autor != null)     							contenidoSets.add(new HashSet<>(getContenidosByAutor(autor)));
		
		if(minAno != null || maxAno != null)  			contenidoSets.add(new HashSet<>(getContenidosByAno(minAno, maxAno)));
		
		if(idioma != null)    							contenidoSets.add(new HashSet<>(getContenidosByIdioma(idioma))); 
		
		if(soporte != null)  							contenidoSets.add(new HashSet<>(getContenidosBySoporte(soporte)));
		
		if(minPaginas != null || maxPaginas != null)    contenidoSets.add(new HashSet<>(getContenidosByPaginas(minPaginas, maxPaginas)));
		
		if(editorial != null) 							contenidoSets.add(new HashSet<>(getContenidosByEditorial(editorial)));
		
		if(isbn != null)      							contenidoSets.add(new HashSet<>(getContenidosByISBN(isbn)));
		
		if(minEdad != null || maxEdad != null)          contenidoSets.add(new HashSet<>(getContenidosByEdadRecomendada(minEdad, maxEdad)));
		
		if(minDuracion != null || maxDuracion != null)  contenidoSets.add(new HashSet<>(getContenidosByDuracion(minDuracion, maxDuracion)));
		 
		if(minCalidad != null || maxCalidad != null)    contenidoSets.add(new HashSet<>(getContenidosByCalidad(minCalidad, maxCalidad)));
		
		if(type != null)      							contenidoSets.add(new HashSet<>(getContenidosByType(type)));		
		
		
		var contenidoList = intersection(contenidoSets).stream().toList();
		
		if(unique) {
			return ContenidoSearchService.getUniqueContenidos(contenidoList);
		}
		return ContenidoSearchService.filterContenidosByDisponibilidadAndPrestable(contenidoList, d,prestable);
	}

}
