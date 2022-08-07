package es.library.databaseserver.prestamos.search.service.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.crud.service.PrestamoService;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;
import es.library.databaseserver.prestamos.search.dao.PrestamoSearchDAO;
import es.library.databaseserver.prestamos.search.service.PrestamoSearchService;

@Service
public class PrestamoSearchServiceImpl implements PrestamoSearchService{

	@Autowired
	private PrestamoSearchDAO searchDAO;
	
	@Autowired
	private PrestamoService crudService;
	
	@Override
	public List<Prestamo> getAllPrestamos() {
		return crudService.getAllPrestamos();
	}

	@Override
	public Prestamo getPrestamoByID(Long id) throws PrestamoNotFoundException {
		return crudService.getPrestamoByID(id);
	}

	@Override
	public List<Prestamo> getPrestamosByIdContenido(Long id) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByIdContenido(id));
	}

	@Override
	public List<Prestamo> getPrestamosIdPerfil(Long id) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosIdPerfil(id));
	}

	@Override
	public List<Prestamo> getPrestamosByDiasDePrestamo(int dias) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByDiasDePrestamo(dias));
	}

	@Override
	public List<Prestamo> getPrestamosByFechaPrestamo(String fecha) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByFechaPrestamo(fecha));
	}

	@Override
	public List<Prestamo> getPrestamosByFechaDevolucion(String fecha) {
		return crudService.idListToPrestamoList(searchDAO.getPrestamosByFechaDevolucion(fecha));
	}

	@Override
	public List<Prestamo> filterDevueltosPrestamos(List<Prestamo> prests, Boolean devueltos) {
		if(devueltos == null) return prests;
		
		return prests.stream()
				.filter(p -> p.isDevuelto() == devueltos)
				.toList();
	}

	public <T> Set<T> intersection(Collection<Set<T>> input) {
	    if(input.isEmpty()) {
	        return Collections.emptySet();
	    } else {
	        Set<T> first = input.iterator().next();
	        return first.stream().filter(e -> input.stream().allMatch(s -> s.contains(e))).collect(Collectors.toCollection(HashSet::new));
	    }
	}
	
	@Override
	public List<Prestamo> getPrestamoByMultipleParams(Long idContenido, Long idPerfil, Integer dias,
			LocalDateTime fromPrestamo, LocalDateTime toPrestamo, LocalDateTime fromDevolucion,
			LocalDateTime toDevolucion, Boolean d) {
		List<Set<Prestamo>> prestamoSet = new ArrayList<>();
		
		if(idContenido != null) prestamoSet.add(new HashSet<>(getPrestamosByIdContenido(idContenido)));
		
		if(idPerfil != null) prestamoSet.add(new HashSet<>(getPrestamosIdPerfil(idPerfil)));
		
		if(fromPrestamo != null || toPrestamo != null) prestamoSet.add(new HashSet<>(getPrestamosBetweenTwoPrestamoDates(fromPrestamo, toPrestamo)));
		
		if(fromDevolucion != null || toDevolucion != null) prestamoSet.add(new HashSet<>(getPrestamosBetweenTwoDevolucionDates(fromDevolucion, toDevolucion)));
		
		return filterDevueltosPrestamos(intersection(prestamoSet).stream().toList(), d);
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
