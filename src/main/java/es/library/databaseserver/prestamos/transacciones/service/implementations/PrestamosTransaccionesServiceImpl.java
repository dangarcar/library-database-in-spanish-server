package es.library.databaseserver.prestamos.transacciones.service.implementations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.crud.service.ContenidoService;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.crud.service.PrestamoService;
import es.library.databaseserver.prestamos.exceptions.IllegalPrestamoException;
import es.library.databaseserver.prestamos.search.service.PrestamoSearchService;
import es.library.databaseserver.prestamos.transacciones.service.PrestamosTransaccionesService;

@Service
public class PrestamosTransaccionesServiceImpl implements PrestamosTransaccionesService{

	@Autowired
	private PrestamoService prestamoService;
	
	@Autowired
	private PrestamoSearchService prestamoSearchService;
	
	@Autowired
	private ContenidoService contenidoService;
	
	@Autowired
	private PerfilService perfilService;

	@Override
	public Prestamo prestar(Long contenidoId, Long perfilId) {
		Perfil perfil = perfilService.getPerfilByID(perfilId);
		Contenido contenido = contenidoService.getContenidoByID(contenidoId);

		if (!contenido.getPrestable())
			throw new IllegalPrestamoException("El contenido " + contenidoId + " no es prestable");
		
		if (!contenido.getDisponible()) {
			throw new IllegalPrestamoException(
					"El contenido " + contenidoId + " ya está prestado." + (contenido.getFechaDisponibilidad() == null
							? ""
							: "Creemos que estara disponible el "+contenido.getFechaDisponibilidad().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
		}
		
		Prestamo prestamo = prestamoService.insertPrestamo(new Prestamo(null, contenido.getID(), perfil.getID(),
				LocalDateTime.now(), null, contenido.getDiasDePrestamo(), false));

		contenido.setDisponible(false);
		contenido.setFechaDisponibilidad(prestamo.getFechaHoraPrestamo()
				.plusDays(contenido.getDiasDePrestamo())
				.toLocalDate());

		contenidoService.updateContenidoByID(contenido.getID(), contenido);
		
		return prestamo;
	}

	@Override
	public Prestamo devolver(Long contenidoId, Long perfilId) {
		@SuppressWarnings("unused")
		Perfil perfil = perfilService.getPerfilByID(perfilId);
		Contenido contenido = contenidoService.getContenidoByID(contenidoId);
		
		if (!contenido.getPrestable())
			throw new IllegalPrestamoException("El contenido " + contenidoId + " no es prestable");
		
		if (contenido.getDisponible()) throw new IllegalPrestamoException("El contenido " + contenidoId + " parece haber sido ya devuelto");
		
		var prestamos = prestamoSearchService.getPrestamoByMultipleParams(contenidoId, perfilId, contenido.getDiasDePrestamo(), null, null, null, null, false);
		if(prestamos.isEmpty()) throw new IllegalPrestamoException("Parece no haber ningun prestamo con el perfil "+perfilId+" y contenido"+contenidoId);
		var p = prestamos.get(0);
		
		p.setDevuelto(true);
		p.setFechaHoraDevolucion(LocalDateTime.now());
		
		Prestamo prestamo = prestamoService.updatePrestamoByID(p.getID(),p);
		
		contenido.setDisponible(true);
		contenido.setFechaDisponibilidad(null);
		
		contenidoService.updateContenidoByID(contenido.getID(), contenido);
		
		return prestamo;
	}

}
