package es.library.databaseserver.contenido.search.dao;

import java.util.List;

import es.library.databaseserver.contenido.Soporte;

public interface ContenidoSearchDAO {

	public List<Long> getContenidosIDByPrompt(String prompt);
	
	
	//Detalles Contenido
	
	public List<Long> getContenidosIDByTitulo(String titulo);
	
	public List<Long> getContenidosIDByAutor(String autor);
	
	public List<Long> getContenidosIDByAno(Integer ano);
	
	public List<Long> getContenidosIDByIdioma(String idioma);
	
	public List<Long> getContenidosIDBySoporte(Soporte soporte);
	
	
	//Detalles Libro
	
	public List<Long> getContenidosIDByPaginas(Integer paginas);
	
	public List<Long> getContenidosIDByEditorial(String editorial);
	
	public List<Long> getContenidosIDByISBN(String isbn);
	
	
	//Detalles audiovisual
	
	public List<Long> getContenidosIDByEdadRecomendada(Integer edad);
	
	public List<Long> getContenidosIDByDuracion(Double duracion);
	
	public List<Long> getContenidosIDByCalidad(Integer calidad);
	
}
