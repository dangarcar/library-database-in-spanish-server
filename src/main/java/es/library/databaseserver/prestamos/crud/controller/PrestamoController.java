package es.library.databaseserver.prestamos.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.crud.service.PrestamoService;

@RequestMapping("/prestamos")
@RestController
public class PrestamoController {

	@Autowired
	private PrestamoService prestamoService;
	
	@GetMapping
	public List<Prestamo> getAllPrestamos() {
		return prestamoService.getAllPrestamos();
	}
	
	@GetMapping(path = "{id}")
	public Prestamo getPrestamoByID(@PathVariable(name = "id") Long id) {
		return prestamoService.getPrestamoByID(id);
	}
	
	@PostMapping
	public Prestamo insertPrestamo(@RequestBody Prestamo prestamo) {
		return prestamoService.insertPrestamo(prestamo);
	}
	
	@DeleteMapping(path = "{id}")
	public void deletePrestamoByID(@PathVariable(name = "id") Long id) {
		prestamoService.deletePrestamoByID(id);
	}
	
	@PutMapping(path = "{id}")
	public Prestamo updatePrestamoByID(@PathVariable(name = "id") Long id, @RequestBody Prestamo prestamo) {
		return prestamoService.updatePrestamoByID(id, prestamo);
	}
	
}
