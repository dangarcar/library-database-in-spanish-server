package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Soporte;

public class Videos extends Audio{
	
	private Integer edadRecomendada;
	private Integer calidad;
	
	public Videos(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			Boolean prestable, Integer diasDePrestamo, Boolean disponible, LocalDate fechaDisponibilidad,
			Double duracion, Long IDAudiovisual, Integer edadRecomendada, Integer calidad) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad, duracion, IDAudiovisual);
		this.edadRecomendada = edadRecomendada;
		this.calidad = calidad;
	}
	
	public Integer getEdadRecomendada() {return edadRecomendada;}
	public void setEdadRecomendada(Integer edadRecomendada) {this.edadRecomendada = edadRecomendada;}
	
	public Integer getCalidad() {return calidad;}
	public void setCalidad(Integer calidad) {this.calidad = calidad;}

	@Override
	public String toString() {
		return super.toString()+"\nVideos [edadRecomendada=" + edadRecomendada + ", calidad=" + calidad + "]";
	}
}
