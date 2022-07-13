package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Soporte;

public class Audio extends ContenidoDTO{
	
	private Double duracion;

	public Audio(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			Boolean prestable, Integer diasDePrestamo, Boolean disponible, LocalDate fechaDisponibilidad,
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
