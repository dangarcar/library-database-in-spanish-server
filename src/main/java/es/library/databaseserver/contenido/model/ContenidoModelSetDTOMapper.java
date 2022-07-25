package es.library.databaseserver.contenido.model;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.dto.Audio;
import es.library.databaseserver.contenido.dto.Libros;
import es.library.databaseserver.contenido.dto.Videos;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;

public class ContenidoModelSetDTOMapper {	
	
	public static Contenido ContenidoModelSetToContenido(ContenidoModelSet cSet) throws NotValidTypeContenidoException, NotValidSoporteException {
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
				dto.getFechaDisponibilidad(), null, null);
		
		if(dto instanceof Audio) {
			var a = (Audio) dto;
			
			c.setIDAudiovisual(a.getIDAudiovisual());
			
			audiovisual = new DetallesAudiovisualModel(a.getIDAudiovisual(), a.getDuracion(), false, 0, 0);
			
			if(dto instanceof Videos) {
				var v = (Videos) dto;
				
				audiovisual.setIsVideo(true);
				audiovisual.setCalidad(v.getCalidad());
				audiovisual.setEdadRecomendada(v.getEdadRecomendada());
			}
		} 
		else if(dto instanceof Libros) {
			var l = (Libros) dto;
			
			c.setIDLibro(l.getIDLibro());
			
			libro = new DetallesLibroModel(l.getIDLibro(), l.getISBN(), l.getPaginas(), l.getEditorial());
		}
		
		return new ContenidoModelSet(c, audiovisual, libro);
	}
	
}
