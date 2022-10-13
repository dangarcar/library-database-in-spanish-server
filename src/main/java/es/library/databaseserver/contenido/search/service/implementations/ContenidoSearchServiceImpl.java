package es.library.databaseserver.contenido.search.service.implementations;

import static es.library.databaseserver.shared.Utils.intersection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.crud.service.ContenidoService;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.search.dao.ContenidoSearchDAO;
import es.library.databaseserver.contenido.search.models.ContenidoModel;
import es.library.databaseserver.contenido.search.service.ContenidoParamsDto;
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
	
	public List<ContenidoModel> getContenidosMasPrestados(int nContenidos) {
		var contenidos = contenidoCRUDService.idListToContenidoList(prestamoSearchDAO.getContenidosMasPrestados(nContenidos));
		return this.contenidoListToModelList(contenidos);
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
	public List<? extends AbstractContenido> getContenidosByMultipleParams(ContenidoParamsDto params) {
		
		List<Set<Contenido>> contenidoSets = new ArrayList<>();	
		
		if(params.getQuery() != null)     										contenidoSets.add(new HashSet<>(getContenidosByPrompt(params.getQuery())));
		
		if(params.getTitulo() != null)    										contenidoSets.add(new HashSet<>(getContenidosByTitulo(params.getTitulo()))); 
		
		if(params.getAutor() != null)     										contenidoSets.add(new HashSet<>(getContenidosByAutor(params.getAutor())));
		
		if(params.getMinAno() != null || params.getMaxAno() != null)  			contenidoSets.add(new HashSet<>(getContenidosByAno(params.getMinAno(), params.getMaxAno())));
		
		if(params.getIdioma() != null)    										contenidoSets.add(new HashSet<>(getContenidosByIdioma(params.getIdioma()))); 
		
		if(params.getSoporte() != null)  										contenidoSets.add(new HashSet<>(getContenidosBySoporte(params.getSoporte())));
		
		if(params.getMinPaginas() != null || params.getMaxPaginas() != null)	contenidoSets.add(new HashSet<>(getContenidosByPaginas(params.getMinPaginas(), params.getMaxPaginas())));
		
		if(params.getEditorial() != null) 										contenidoSets.add(new HashSet<>(getContenidosByEditorial(params.getEditorial())));
		
		if(params.getIsbn() != null)      										contenidoSets.add(new HashSet<>(getContenidosByISBN(params.getIsbn())));
		
		if(params.getMinEdad() != null || params.getMaxEdad() != null)      	contenidoSets.add(new HashSet<>(getContenidosByEdadRecomendada(params.getMinEdad(), params.getMaxEdad())));
		
		if(params.getMinDuracion() != null || params.getMaxDuracion() != null)  contenidoSets.add(new HashSet<>(getContenidosByDuracion(params.getMinDuracion(), params.getMaxDuracion())));
		 
		if(params.getMinCalidad() != null || params.getMaxCalidad() != null)    contenidoSets.add(new HashSet<>(getContenidosByCalidad(params.getMinCalidad(), params.getMaxCalidad())));
		
		if(params.getType() != null)      										contenidoSets.add(new HashSet<>(getContenidosByType(params.getType())));		
		
		var contenidoList = intersection(contenidoSets).stream().toList();
		
		if(params.getUnique()) {
			return ContenidoSearchService.getUniqueContenidos(contenidoList);
		}
		return ContenidoSearchService.filterContenidosByDisponibilidadAndPrestable(contenidoList, params.getDisponible(), params.getPrestable());
	}

	@SuppressWarnings("unchecked")
	private List<ContenidoModel> contenidoListToModelList(List<Contenido> contenidos) {
		Set<ContenidoModel> modelos = new HashSet<>();
		
		for(Contenido c: contenidos) {
			ContenidoParamsDto dto = new ContenidoParamsDto(null, 
					c.getTitulo(), 
					c.getAutor(), 
					c.getAno(), 
					c.getAno(), 
					c.getIdioma(), 
					c.getSoporte(), 
					null, null, null, null, null, null, null, null, null, null, null, null, 
					true, null);
			
			if(c instanceof Audio a) {
				dto.setMaxDuracion(a.getDuracion());
				dto.setMinDuracion(a.getDuracion());
			}
			if(c instanceof Video v) {
				dto.setMaxEdad(v.getEdadRecomendada());
				dto.setMinEdad(v.getEdadRecomendada());
				dto.setMaxCalidad(v.getCalidad());
				dto.setMinCalidad(v.getCalidad());
			}
			if(c instanceof Libro l) {
				dto.setIsbn(l.getISBN());
				dto.setMaxPaginas(l.getPaginas());
				dto.setMinPaginas(l.getPaginas());
				dto.setEditorial(l.getEditorial());
			}
			
			var resultadoC = (List<ContenidoModel>) this.getContenidosByMultipleParams(dto);
			
			modelos.addAll(resultadoC);
		}
		
		return modelos.stream().toList();
	}
	
}
