package es.library.databaseserver.personalspace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.model.PerfilResponse;
import es.library.databaseserver.personalspace.service.MyPerfilService;
import es.library.databaseserver.personalspace.service.PrestamosService;
import es.library.databaseserver.prestamos.Prestamo;

@RestController
@RequestMapping("/user")
public class MiCuentaController {

	@Autowired
	private PrestamosService prestamoService;
	
	@Autowired
	private MyPerfilService myPerfilService;
	
	@PostMapping(path = "/prestar/{id}")
	public Prestamo prestar(@PathVariable(name = "id") Long contenidoId, Authentication auth) {		
		return prestamoService.prestar(contenidoId, (String) auth.getPrincipal());
	}
	
	@PostMapping(path = "/devolver/{id}")
	public Prestamo devolver(@PathVariable(name = "id") Long contenidoId, Authentication auth) {		
		return prestamoService.devolver(contenidoId, (String) auth.getPrincipal());
	}
	
	@GetMapping
	public PerfilResponse getMyInfo(Authentication auth) {
		return new PerfilResponse(myPerfilService.getMyInfo((String) auth.getPrincipal()));
	}
	
	@GetMapping(path = "/prestamos/historial")
	public List<Contenido> getHistorialDePrestamos(Authentication auth) {
		return myPerfilService.getHistorialDePrestamos((String) auth.getPrincipal());
	}
	
	@GetMapping(path = "/prestamos/todos")
	public List<Prestamo> getMyPrestamos(Authentication auth){
		return myPerfilService.getMyPrestamos((String) auth.getPrincipal());
	}
	
	@GetMapping(path = "/prestamos")
	public List<Contenido> getContenidosEnPrestamo(Authentication auth) {
		return myPerfilService.getContenidosEnPrestamo((String) auth.getPrincipal());
	}
	
	@PutMapping
	public PerfilResponse updateMyAccount(Authentication auth, @RequestBody Perfil perfil) {
		return new PerfilResponse(myPerfilService.updateMyAccount(((String) auth.getPrincipal()), perfil));
	}
	
}
