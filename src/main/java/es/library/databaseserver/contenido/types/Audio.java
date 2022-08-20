package es.library.databaseserver.contenido.types;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;

public class Audio extends Contenido{
	
	private Double duracion;

	public Audio(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			boolean prestable, Integer diasDePrestamo, boolean disponible, LocalDate fechaDisponibilidad,
			Double duracion) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad);
		this.duracion = duracion;
	}
	
	public Double getDuracion() {return duracion;}
	public void setDuracion(Double duracion) {this.duracion = duracion;}

	@Override
	public String toString() {
		return super.toString()+"\nAudio [duracion=" + duracion +"]";
	}
}
