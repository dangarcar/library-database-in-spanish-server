package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;

public class Audio extends Contenido{
	
	private Double duracion;
	private Long IDAudiovisual;

	public Audio(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			Boolean prestable, Integer diasDePrestamo, Boolean disponible, LocalDate fechaDisponibilidad,
			Double duracion, Long IDAudiovisual) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad, null, IDAudiovisual);
		this.duracion = duracion;
		this.IDAudiovisual = IDAudiovisual;
	}
	
	public Double getDuracion() {return duracion;}
	public void setDuracion(Double duracion) {this.duracion = duracion;}

	public Long getIDAudiovisual() {return IDAudiovisual;}
	public void setIDAudiovisual(Long iDAudiovisual) {IDAudiovisual = iDAudiovisual;}

	@Override
	public String toString() {
		return super.toString()+"\nAudio [duracion=" + duracion +"]";
	}
}
