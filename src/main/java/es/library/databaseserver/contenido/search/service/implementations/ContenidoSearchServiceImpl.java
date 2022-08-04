package es.library.databaseserver.contenido.search.service.implementations;

import java.util.List;
import java.util.Map;

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

}
