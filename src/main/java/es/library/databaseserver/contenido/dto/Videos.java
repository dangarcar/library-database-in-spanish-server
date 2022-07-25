package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Soporte;

public class Videos extends Audio{
	
	private int edadRecomendada;
	private int calidad;
	
	public Videos(Long iD, String titulo, String autor, String descripcion, int ano, String idioma, Soporte soporte,
			boolean prestable, int diasDePrestamo, boolean disponible, LocalDate fechaDisponibilidad,
			double duracion, long IDAudiovisual, int edadRecomendada, int calidad) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad, duracion, IDAudiovisual);
		this.edadRecomendada = edadRecomendada;
		this.calidad = calidad;
	}
	
	public int getEdadRecomendada() {return edadRecomendada;}
	public void setEdadRecomendada(int edadRecomendada) {this.edadRecomendada = edadRecomendada;}
	
	public int getCalidad() {return calidad;}
	public void setCalidad(int calidad) {this.calidad = calidad;}

	@Override
	public String toString() {
		return super.toString()+"\nVideos [edadRecomendada=" + edadRecomendada + ", calidad=" + calidad + "]";
	}
}
