package es.library.databaseserver.contenido.crud.model;

import org.springframework.stereotype.Component;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.IllegalContenidoException;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;
import es.library.databaseserver.contenido.types.Video;

@Component
public class ContenidoValidator {
	
	public boolean validateNumberGreaterThanZero(int number) {
		return number >= 0;
	}
	
	public boolean validateNumberGreaterThanZero(double number) {
		return number >= 0;
	}
	
	public boolean soporteForAudio(Soporte soporte) {
		return soporte.isAudio();
	}

	public boolean soporteForVideo(Soporte soporte) {
		return soporte.isAudio() && soporte.isMultimedia();
	}
	
	public boolean soporteForLibro(Soporte soporte) {
		return !soporte.isAudio() && !soporte.isMultimedia();
	}
	
	public void validateContenidoCorrect(Contenido contenido) throws IllegalContenidoException {
		StringBuilder builder = new StringBuilder();
		
		if(!validateNumberGreaterThanZero(contenido.getAno())) {
			builder.append(System.lineSeparator());
			builder.append("El año de creación del contenido debe ser mayor que cero");
		}
		
		if(contenido.getDiasDePrestamo()!= null && !validateNumberGreaterThanZero(contenido.getDiasDePrestamo())) {
			builder.append(System.lineSeparator());
			builder.append("El numero de dias que un contenido puede ser prestado debe ser mayor que cero");
		}
		
		if(contenido.getTitulo() == null) {
			builder.append(System.lineSeparator());
			builder.append("El titulo no puede ser nulo");
		}
		
		
		if(contenido instanceof Audio) {
			var c = (Audio) contenido;
			
			if(!soporteForAudio(c.getSoporte())) {
				builder.append(System.lineSeparator());
				builder.append("El soporte debe ser compatible con audio");
			}
			
			if(!validateNumberGreaterThanZero(c.getDuracion())) {
				builder.append(System.lineSeparator());
				builder.append("Un contenido audiovisual debe durar más de 0 segundos");
			}
			
			if(contenido instanceof Video) {
				var v = (Video) c;
				
				if(!soporteForVideo(v.getSoporte())) {
					builder.append(System.lineSeparator());
					builder.append("El soporte debe ser compatible con vídeo");
				}
				
				if(!validateNumberGreaterThanZero(v.getEdadRecomendada())) {
					builder.append(System.lineSeparator());
					builder.append("La edad recomendada no puede ser menor de cero");
				}
				
				if(!validateNumberGreaterThanZero(v.getCalidad())) {
					builder.append(System.lineSeparator());
					builder.append("Un vídeo no puede tener un número negativo de calidad");
				}
			}
		}
		else if (contenido instanceof Libro) {
			var l = (Libro) contenido;
			
			if(!soporteForLibro(l.getSoporte())) {
				builder.append(System.lineSeparator());
				builder.append("El soporte debe ser compatible con libro");
			}
			
			if(l.getISBN() == null) {
				builder.append(System.lineSeparator());
				builder.append("El ISBN de los libros no debe ser nulo");
			}
			
			if (!validateNumberGreaterThanZero(l.getPaginas())) {
				builder.append(System.lineSeparator());
				builder.append("Un libro debe de tener alguna página");
			}
		}
		
		if(!builder.isEmpty()) throw new IllegalContenidoException(builder.toString());
	}
	
	public void validateContenidoCorrectUpdating(Contenido contenido) throws IllegalContenidoException {
		StringBuilder builder = new StringBuilder();
		
		if(contenido.getAno()!=null) if(!validateNumberGreaterThanZero(contenido.getAno())) {
			builder.append(System.lineSeparator());
			builder.append("El año de creación del contenido debe ser mayor que cero");
		}
		
		if(contenido.getDiasDePrestamo()!=null) if(!validateNumberGreaterThanZero(contenido.getDiasDePrestamo())) {
			builder.append(System.lineSeparator());
			builder.append("El numero de dias que un contenido puede ser prestado debe ser mayor que cero");
		}
		
		
		if(contenido instanceof Audio) {
			var c = (Audio) contenido;
			
			if(contenido.getSoporte()!=null) if(!soporteForAudio(c.getSoporte())) {
				builder.append(System.lineSeparator());
				builder.append("El soporte debe ser compatible con audio");
			}
			
			if(c.getDuracion()!=null) if(!validateNumberGreaterThanZero(c.getDuracion())) {
				builder.append(System.lineSeparator());
				builder.append("Un contenido audiovisual debe durar más de 0 segundos");
			}
			
			if(contenido instanceof Video) {
				var v = (Video) c;
				
				if(contenido.getSoporte()!=null) if(!soporteForVideo(v.getSoporte())) {
					builder.append(System.lineSeparator());
					builder.append("El soporte debe ser compatible con vídeo");
				}
				
				if(v.getEdadRecomendada()!=null) if(!validateNumberGreaterThanZero(v.getEdadRecomendada())) {
					builder.append(System.lineSeparator());
					builder.append("La edad recomendada no puede ser menor de cero");
				}
				
				if(v.getCalidad()!=null) if(!validateNumberGreaterThanZero(v.getCalidad())) {
					builder.append(System.lineSeparator());
					builder.append("Un vídeo no puede tener un número negativo de calidad");
				}
			}
		}
		else if (contenido instanceof Libro) {
			var l = (Libro) contenido;
			
			if(contenido.getSoporte()!=null) if(!soporteForLibro(l.getSoporte())) {
				builder.append(System.lineSeparator());
				builder.append("El soporte debe ser compatible con libro");
			}
			
			if(l.getPaginas()!=null) if (!validateNumberGreaterThanZero(l.getPaginas())) {
				builder.append(System.lineSeparator());
				builder.append("Un libro debe de tener alguna página");
			}
		}
		
		if(!builder.isEmpty()) throw new IllegalContenidoException(builder.toString());
	}
	
}
