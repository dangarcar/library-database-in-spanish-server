package es.library.databaseserver.contenido.types;

import java.time.LocalDate;

public class Video extends Audio{
	
	private Integer edadRecomendada;
	private Integer calidad;
	
	public Video(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			boolean prestable, Integer diasDePrestamo, boolean disponible, LocalDate fechaDisponibilidad,
			Double duracion, Integer edadRecomendada, Integer calidad) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad, duracion);
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
