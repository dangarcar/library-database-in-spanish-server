package es.library.databaseserver.contenido.search.models;

import java.net.URL;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.types.Video;

public class VideoModel extends ContenidoModel {

	private Double duracion;
	private Integer edadRecomendada;
	private Integer calidad;

	public VideoModel(String titulo, String autor, String descripcion, int ano, String idioma,
			Soporte soporte, URL imagen, Double duracion, Integer edadRecomendada, Integer calidad) {
		super(Type.video, titulo, autor, descripcion, ano, idioma, soporte, imagen);
		this.duracion = duracion;
		this.edadRecomendada = edadRecomendada;
		this.calidad = calidad;
	}

	public Double getDuracion() {
		return duracion;
	}

	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}

	public Integer getEdadRecomendada() {
		return edadRecomendada;
	}

	public void setEdadRecomendada(Integer edadRecomendada) {
		this.edadRecomendada = edadRecomendada;
	}

	public Integer getCalidad() {
		return calidad;
	}

	public void setCalidad(Integer calidad) {
		this.calidad = calidad;
	}

	@Override
	public String toString() {
		return super.toString()+"\nVideoModel [duracion=" + duracion + ", edadRecomendada=" + edadRecomendada + ", calidad=" + calidad
				+ "]";
	}

	@Override
	public Contenido toContenido() {
		return new Video(null, titulo, autor, descripcion, ano, idioma, soporte, false, null, false, imagen, duracion, edadRecomendada, calidad);
	}
	
	
	
}
