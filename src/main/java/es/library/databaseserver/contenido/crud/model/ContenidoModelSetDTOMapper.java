package es.library.databaseserver.contenido.crud.model;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;
import es.library.databaseserver.contenido.types.Video;

public class ContenidoModelSetDTOMapper {	
	
	public static Contenido ContenidoModelSetToContenido(ContenidoModelSet cSet) throws NotValidTypeContenidoException, NotValidSoporteException {
		Contenido dto = null;
		
		if(cSet != null) {
			Contenido c = cSet.getContenido();
			DetallesAudiovisualModel a = cSet.getAudiovisual();
			DetallesLibroModel l= cSet.getLibro();
			
			switch(cSet.getType()) {
			case ContenidoModelSet.LIBRO:
				dto = new Libro(
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
						l.getEditorial()
					);
				dto.setIDLibro(l.getID());
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
						a.getDuracion()
					);
				dto.setIDAudiovisual(a.getID());
				break;
			case ContenidoModelSet.VIDEO:
				dto = new Video(
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
						a.getEdadRecomendada(),
						a.getCalidad()
					);
				dto.setIDAudiovisual(a.getID());
				break;
			}
		}
		
		return dto;
	}
	
	public static ContenidoModelSet ContenidoToContenidoModelSet(Contenido dto) throws NotValidTypeContenidoException, NotValidSoporteException {		
		DetallesAudiovisualModel audiovisual = null;
		DetallesLibroModel libro = null;
		Contenido c = new Contenido(
				dto.getID(), 
				dto.getTitulo(), 
				dto.getAutor(), 
				dto.getDescripcion(), 
				dto.getAno(), 
				dto.getIdioma(), 
				dto.getSoporte(), 
				dto.getPrestable(), 
				dto.getDiasDePrestamo(), 
				dto.getDisponible(), 
				dto.getFechaDisponibilidad());
		
		if(dto instanceof Audio) {
			var a = (Audio) dto;
			
			c.setIDAudiovisual(a.getIDAudiovisual());
			
			audiovisual = new DetallesAudiovisualModel(a.getIDAudiovisual(), a.getDuracion(), false, null, null);
			
			if(dto instanceof Video) {
				var v = (Video) dto;
				
				audiovisual.setIsVideo(true);
				audiovisual.setCalidad(v.getCalidad());
				audiovisual.setEdadRecomendada(v.getEdadRecomendada());
			}
		} 
		else if(dto instanceof Libro) {
			var l = (Libro) dto;
			
			c.setIDLibro(l.getIDLibro());
			
			libro = new DetallesLibroModel(l.getIDLibro(), l.getISBN(), l.getPaginas(), l.getEditorial());
		}
		
		return new ContenidoModelSet(c, audiovisual, libro);
	}
	
}
