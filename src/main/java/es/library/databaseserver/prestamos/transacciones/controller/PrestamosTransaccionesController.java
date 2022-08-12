package es.library.databaseserver.prestamos.transacciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.prestamos.transacciones.service.PrestamosTransaccionesService;

@RestController
public class PrestamosTransaccionesController {

	@Autowired
	private PrestamosTransaccionesService transaccionesService;
	
	@PostMapping(path = "/prestar")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void prestar(
			@RequestParam(name = "cont") Long contenidoId,
			@RequestParam(name = "perf") Long perfilId) {
		transaccionesService.prestar(contenidoId, perfilId);
	}
	
	@PostMapping(path = "/devolver")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void devolver(
			@RequestParam(name = "cont") Long contenidoId,
			@RequestParam(name = "perf") Long perfilId) {
		transaccionesService.devolver(contenidoId, perfilId);
	}
	
}
