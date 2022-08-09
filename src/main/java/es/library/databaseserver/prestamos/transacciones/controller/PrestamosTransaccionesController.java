package es.library.databaseserver.prestamos.transacciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.prestamos.transacciones.service.PrestamosTransaccionesService;

@RestController
public class PrestamosTransaccionesController {

	@Autowired
	private PrestamosTransaccionesService transaccionesService;
	
	@PostMapping(path = "/prestar")
	public void prestar(
			@RequestParam(name = "cont") Long contenidoId,
			@RequestParam(name = "perf") Long perfilId) {
		transaccionesService.prestar(contenidoId, perfilId);
	}
	
	@PostMapping(path = "/devolver")
	public void devolver(
			@RequestParam(name = "cont") Long contenidoId,
			@RequestParam(name = "perf") Long perfilId) {
		transaccionesService.devolver(contenidoId, perfilId);
	}
	
}
