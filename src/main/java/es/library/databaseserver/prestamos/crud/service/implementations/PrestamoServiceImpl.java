package es.library.databaseserver.prestamos.crud.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.crud.dao.PrestamoDAO;
import es.library.databaseserver.prestamos.crud.model.PrestamoValidator;
import es.library.databaseserver.prestamos.crud.service.PrestamoService;
import es.library.databaseserver.prestamos.exceptions.IllegalPrestamoException;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;

@Service
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
	private PrestamoDAO prestamoDAO;
	
	@Autowired
	private PrestamoValidator validator;
	
	@Override
	public List<Prestamo> getAllPrestamos() {
		return prestamoDAO.getAllPrestamosID()
				.stream()
				.map(id -> getPrestamoByID(id))
				.collect(Collectors.toList());
	}

	@Override
	public Prestamo getPrestamoByID(Long id) throws PrestamoNotFoundException {
		return prestamoDAO.getPrestamoByID(id)
				.orElseThrow(() -> new PrestamoNotFoundException("El prestamo con id "+id+" no existe en la base de datos"));
	}

	@Override
	public Prestamo insertPrestamo(Prestamo prestamo) throws IllegalPrestamoException {
		//Compruebo si el prestamo es correcto
		validator.validatePrestamoCorrect(prestamo);
		
		return prestamoDAO.insertPrestamo(prestamo);
	}

	@Override
	public void deletePrestamoByID(Long id) throws PrestamoNotFoundException {
		prestamoDAO.deletePrestamoByID(id);
	}

	@Override
	public Prestamo updatePrestamoByID(Long id, Prestamo prestamo) throws IllegalPrestamoException, PrestamoNotFoundException {
		//Compruebo si el prestamo es correcto
		validator.validatePrestamoCorrect(prestamo);
		
		return prestamoDAO.updatePrestamoByID(id, prestamo);
	}
	
}
