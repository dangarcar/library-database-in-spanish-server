package es.library.databaseserver.prestamos.search.service.implementations;

import static es.library.databaseserver.shared.Utils.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.crud.service.ContenidoService;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;
import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.crud.service.PrestamoService;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;
import es.library.databaseserver.prestamos.search.dao.PrestamoSearchDAO;
import es.library.databaseserver.prestamos.search.service.PrestamoSearchService;
import es.library.databaseserver.prestamos.transacciones.service.PrestamosTransaccionesService;

@Service
public class PrestamoSearchServiceImpl implements PrestamoSearchService{

	@Autowired
	private PrestamoSearchDAO searchDAO;
	
	@Autowired
	private PrestamoService crudService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private ContenidoService contenidoService;
	
	@Override
	public List<Prestamo> getAllPrestamos() {
		return crudService.getAllPrestamos();
	}

	@Override
	public Prestamo getPrestamoByID(Long id) throws PrestamoNotFoundException {
		return crudService.getPrestamoByID(id);
	}

	@Override
	public List<Prestamo> getPrestamosByIdContenido(Long id) throws ContenidoNotFoundException {
		//Compruebo que el contenido existe, sino lanzo una excepcion
		try {
			contenidoService.getContenidoByID(id);
		} catch (ContenidoNotFoundException e) {
			throw new ContenidoNotFoundException(PrestamosTransaccionesService.CONTENIDO_PREFIX+e.getMessage());
		}
		
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByIdContenido(id));
	}

	@Override
	public List<Prestamo> getPrestamosIdPerfil(Long id) throws PerfilNotFoundException {
		//Compruebo que el perfil existe, sino lanzo una excepcion
		try{
			perfilService.getPerfilByID(id);
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundException(PrestamosTransaccionesService.PERFIL_PREFIX+e.getMessage());
		}
		
		return crudService.idListToPrestamoList(searchDAO.getPrestamosIdPerfil(id));
	}

	@Override
	public List<Prestamo> getPrestamosByDiasDePrestamo(int dias) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByDiasDePrestamo(dias));
	}
	@Override
	public List<Prestamo> getPrestamosByDiasDePrestamo(Integer min, Integer max) {
		if(min == null) min = 0;
		if(max == null) max = Integer.MAX_VALUE;
		
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByDiasDePrestamo(min, max));
	}

	@Override
	public List<Prestamo> getPrestamosByFechaPrestamo(String fecha) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByFechaPrestamo(fecha));
	}

	@Override
	public List<Prestamo> getPrestamosByFechaDevolucion(String fecha) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByFechaDevolucion(fecha));
	}

	public List<Contenido> getContenidosByPerfilID(Long idPerfil, Boolean d) {
		List<Prestamo> prestamosFiltered = PrestamoSearchService.filterDevueltosPrestamos(getPrestamosIdPerfil(idPerfil), d);
		
		List<Long> contenidosList = prestamosFiltered.stream().map(Prestamo::getIDContenido).toList();
		
		return contenidoService.idListToContenidoList(contenidosList);
	}
	
	@Override
	public List<Prestamo> getPrestamoByMultipleParams(Long idContenido, Long idPerfil, Integer minDias, Integer maxDias,
			LocalDateTime fromPrestamo, LocalDateTime toPrestamo, LocalDateTime fromDevolucion,
			LocalDateTime toDevolucion, Boolean d) {
		List<Set<Prestamo>> prestamoSet = new ArrayList<>();
		
		if(idContenido != null) 							prestamoSet.add(new HashSet<>(getPrestamosByIdContenido(idContenido)));
		if(idPerfil != null) 								prestamoSet.add(new HashSet<>(getPrestamosIdPerfil(idPerfil)));
		if(minDias != null || maxDias != null)				prestamoSet.add(new HashSet<>(getPrestamosByDiasDePrestamo(minDias, maxDias)));
		if(fromPrestamo != null || toPrestamo != null) 		prestamoSet.add(new HashSet<>(getPrestamosBetweenTwoPrestamoDates(fromPrestamo, toPrestamo)));
		if(fromDevolucion != null || toDevolucion != null) 	prestamoSet.add(new HashSet<>(getPrestamosBetweenTwoDevolucionDates(fromDevolucion, toDevolucion)));
		
		return PrestamoSearchService.filterDevueltosPrestamos(intersection(prestamoSet).stream().toList(), d);
	}

	@Override
	public List<Prestamo> getPrestamosBetweenTwoPrestamoDates(LocalDateTime from, LocalDateTime to) {
		if(from == null) from = LocalDateTime.MIN;
		if(to == null) to = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
		
		return crudService.idListToPrestamoList(searchDAO.getPrestamosBetweenTwoPrestamoDates(from, to));
	}

	@Override
	public List<Prestamo> getPrestamosBetweenTwoDevolucionDates(LocalDateTime from, LocalDateTime to) {
		if(from == null) from = LocalDateTime.MIN;
		if(to == null) to = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
		
		return crudService.idListToPrestamoList(searchDAO.getPrestamosBetweenTwoDevolucionDates(from, to));
	}

}
