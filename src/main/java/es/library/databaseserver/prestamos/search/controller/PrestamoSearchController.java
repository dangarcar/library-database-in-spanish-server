package es.library.databaseserver.prestamos.search.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;
import es.library.databaseserver.prestamos.search.service.PrestamoSearchService;

@RequestMapping("/prestamos/search")
@RestController
public class PrestamoSearchController {

	@Autowired
	private PrestamoSearchService searchService;
	
	@GetMapping(path = "/id/{id}")
	public Prestamo getPrestamoByID(@PathVariable(name = "id") Long id) throws PrestamoNotFoundException {
		return searchService.getPrestamoByID(id);
	}
	
	@GetMapping(path = "/contenido/{idCon}")
	public List<Prestamo> getPrestamosByIdContenido(@PathVariable(name = "idCon") Long id, @RequestParam(required = false) Boolean d) {
		var a = searchService.getPrestamosByIdContenido(id);
		
		return searchService.filterDevueltosPrestamos(a, d);
	}
	
	@GetMapping(path = "/perfil/{idPer}")
	public List<Prestamo> getPrestamosIdPerfil(@PathVariable(name = "idPer") Long id, @RequestParam(required = false) Boolean d) {
		var a = searchService.getPrestamosIdPerfil(id);
		
		return searchService.filterDevueltosPrestamos(a, d);
	}
	
	@GetMapping(path = "dias/{dias}")
	public List<Prestamo> getPrestamosByDiasDePrestamo(@PathVariable(name = "dias") int dias, @RequestParam(required = false) Boolean d) {
		var a = searchService.getPrestamosByDiasDePrestamo(dias);
		
		return searchService.filterDevueltosPrestamos(a, d);
	}
	
	@GetMapping(path = "fecha/{fecha}")
	public List<Prestamo> getPrestamosByFechaPrestamo(@PathVariable(name = "fecha") String fecha, @RequestParam(required = false) Boolean d) {
		var a = searchService.getPrestamosByFechaPrestamo(fecha);
		
		return searchService.filterDevueltosPrestamos(a, d);
	}
	
	@GetMapping(path = "devolucion/{fecha}")
	public List<Prestamo> getPrestamosByFechaDevolucion(@PathVariable(name = "fecha") String fecha) {
		return searchService.getPrestamosByFechaDevolucion(fecha);
	}
	
	@GetMapping
	public List<Prestamo> getPrestamosByParams(
			@RequestParam(required = false,name = "contenido") Long idContenido,
			@RequestParam(required = false,name = "perfil") Long idPerfil,
			@RequestParam(required = false) Integer dias,
			@RequestParam(required = false) String fromPrestamo,
			@RequestParam(required = false) String toPrestamo,
			@RequestParam(required = false) String fromDevolucion,
			@RequestParam(required = false) String toDevolucion,
			@RequestParam(required = false) Boolean d){
		if(idContenido==null && idPerfil==null && dias==null && fromPrestamo==null && toPrestamo==null && fromDevolucion==null && toDevolucion==null) {
			return searchService.filterDevueltosPrestamos(searchService.getAllPrestamos(), d);
		}
		
		return searchService.getPrestamoByMultipleParams(
				idContenido, 
				idPerfil, 
				dias, 
				fromPrestamo!=null? LocalDateTime.parse(fromPrestamo):null, 
				toPrestamo!= null? LocalDateTime.parse(toPrestamo):null, 
				fromDevolucion!=null? LocalDateTime.parse(fromDevolucion):null, 
				toDevolucion!=null? LocalDateTime.parse(toDevolucion):null, 
				d
			);
	}
}
