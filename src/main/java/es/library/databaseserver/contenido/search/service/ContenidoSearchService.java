package es.library.databaseserver.contenido.search.service;

import java.util.List;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;

public interface ContenidoSearchService {

	public List<Contenido> getAllContenidos();
	
	public Contenido getContenidoById(Long id) throws ContenidoNotFoundException;
	
	public List<Contenido> getContenidosByTitulo(String titulo);
	
	public List<Contenido> getContenidosByAutor(String autor);
	
	public List<Contenido> getContenidosIDByAno(Integer ano);
	
	public List<Contenido> getContenidosIDByIdioma(String idioma);
	
	public List<Contenido> getContenidosIDBySoporte(Soporte soporte);
}
