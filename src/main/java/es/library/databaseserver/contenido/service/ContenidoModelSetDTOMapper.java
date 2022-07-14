package es.library.databaseserver.contenido.service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.dto.Audio;
import es.library.databaseserver.contenido.dto.Libros;
import es.library.databaseserver.contenido.dto.Videos;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModelSet;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

public class ContenidoModelSetDTOMapper {	
	
	public static Contenido ContenidoModelSetToContenido(ContenidoModelSet cSet) throws NotValidTypeContenidoException {
		Contenido dto = null;
		
		if(cSet != null) {
			Contenido c = cSet.getContenido();
			DetallesAudiovisualModel a = cSet.getAudiovisual();
			DetallesLibroModel l= cSet.getLibro();
			
			switch(cSet.getType()) {
			case ContenidoModelSet.LIBRO:
				dto = new Libros(
						c.getID(), 
						c.getTitulo(), 
						c.getAutor(), 
						c.getDescripcion(), 
						c.getAno(), 
						c.getIdioma(), 
						c.getSoporte(), 
						c.getPrestable(), 
						c.getDiasDePrestamo(), 
						c.getDisponible(), 
						c.getFechaDisponibilidad(), 
						l.getISBN(), 
						l.getPaginas(), 
						l.getEditorial(), 
						l.getID()
					);
				break;
			case ContenidoModelSet.AUDIO:
				dto = new Audio(
						c.getID(), 
						c.getTitulo(), 
						c.getAutor(), 
						c.getDescripcion(), 
						c.getAno(), 
						c.getIdioma(), 
						c.getSoporte(), 
						c.getPrestable(), 
						c.getDiasDePrestamo(), 
						c.getDisponible(), 
						c.getFechaDisponibilidad(),
						a.getDuracion(),
						a.getID()
						
					);
				break;
			case ContenidoModelSet.VIDEO:
				dto = new Videos(
						c.getID(), 
						c.getTitulo(), 
						c.getAutor(), 
						c.getDescripcion(), 
						c.getAno(), 
						c.getIdioma(), 
						c.getSoporte(), 
						c.getPrestable(), 
						c.getDiasDePrestamo(), 
						c.getDisponible(), 
						c.getFechaDisponibilidad(),
						a.getDuracion(),
						a.getID(),
						a.getEdadRecomendada(),
						a.getCalidad()
					);
				break;
			}
		}
		
		return dto;
	}
	
	//TODO
	public static ContenidoModelSet ContenidoToContenidoModelSet(Contenido dto) {
		ContenidoModelSet cSet = null;
		
		
		
		return cSet;
	}
	
}
