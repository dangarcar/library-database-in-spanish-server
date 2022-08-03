package es.library.databaseserver.contenido.search.service.implementations;

import java.util.List;

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
	public List<Contenido> getContenidosIDByAno(Integer ano) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByAno(ano));
	}

	@Override
	public List<Contenido> getContenidosIDByIdioma(String idioma) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDByIdioma(idioma));
	}

	@Override
	public List<Contenido> getContenidosIDBySoporte(Soporte soporte) {
		return contenidoCRUDService.idListToContenidoList(contenidoSearchDAO.getContenidosIDBySoporte(soporte));
	}

}
