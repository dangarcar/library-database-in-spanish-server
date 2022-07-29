package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.IllegalContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;

public class Audio extends Contenido{
	
	private double duracion;

	public Audio(Long iD, String titulo, String autor, String descripcion, int ano, String idioma, Soporte soporte,
			boolean prestable, int diasDePrestamo, boolean disponible, LocalDate fechaDisponibilidad,
			double duracion) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad);
		this.duracion = duracion;
	}
	
	public double getDuracion() {return duracion;}
	public void setDuracion(double duracion) {this.duracion = duracion;}

	@Override
	public String toString() {
		return super.toString()+"\nAudio [duracion=" + duracion +"]";
	}
	
	public void checkIsCorrect() throws IllegalContenidoException {
		super.checkIsCorrect();
		
		if(!getSoporte().isAudio()) throw new IllegalContenidoException("El soporte no es correcto",new NotValidSoporteException("El soporte debe ser compatible con audio"));
	}
}
