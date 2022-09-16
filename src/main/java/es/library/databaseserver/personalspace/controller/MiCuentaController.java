package es.library.databaseserver.personalspace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import es.library.databaseserver.security.authentication.AuthenticationService;

@RestController
@RequestMapping("/user")
public class MiCuentaController {

	@Autowired
	private PrestamosService prestamoService;
	
	@Autowired
	private MyPerfilService myPerfilService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping(path = "/prestar/{id}")
	public Prestamo prestar(@PathVariable(name = "id") Long contenidoId, Authentication auth) {		
		return prestamoService.prestar(contenidoId, auth.getName());
	}
	
	@PostMapping(path = "/devolver/{id}")
	public Prestamo devolver(@PathVariable(name = "id") Long contenidoId, Authentication auth) {		
		return prestamoService.devolver(contenidoId, auth.getName());
	}
	
	@GetMapping
	public PerfilResponse getMyInfo(Authentication auth) {
		return new PerfilResponse(myPerfilService.getMyInfo(auth.getName()));
	}
	
	@GetMapping(path = "/prestamos/historial")
	public List<Contenido> getHistorialDePrestamos(Authentication auth) {
		return myPerfilService.getHistorialDePrestamos(auth.getName());
	}
	
	@GetMapping(path = "/prestamos/todos")
	public List<Prestamo> getMyPrestamos(Authentication auth){
		return myPerfilService.getMyPrestamos(auth.getName());
	}
	
	@GetMapping(path = "/prestamos")
	public List<Contenido> getContenidosEnPrestamo(Authentication auth) {
		return myPerfilService.getContenidosEnPrestamo(auth.getName());
	}
	
	@PutMapping
	public PerfilResponse updateMyAccount(Authentication auth, @RequestBody Perfil perfil) {
		return new PerfilResponse(myPerfilService.updateMyAccount(auth.getName(), perfil));
	}
	
	@DeleteMapping("/delete")
	public void deletePerfil(Authentication authentication) {
		authenticationService.deleteProfile(authentication.getName());
	}
	
	@PostMapping(path = "/logout")
	public void logout(Authentication authentication) {
		authenticationService.logout(authentication.getName());
	}
	
}
