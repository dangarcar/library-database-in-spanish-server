package es.library.databaseserver.contenido.search.models;

import java.net.URL;
import java.util.Objects;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.types.Audio;

public class AudioModel extends ContenidoModel {

	private Double duracion;
	
	public AudioModel(String titulo, String autor, String descripcion, int ano, String idioma, Soporte soporte, URL imagen, Double duracion) {
		super(Type.audio, titulo, autor, descripcion, ano, idioma, soporte, imagen);
		this.duracion = duracion;
	}

	public Double getDuracion() {
		return duracion;
	}

	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return super.toString()+"\nAudioModel [duracion=" + duracion + "]";
	}

	@Override
	public Contenido toContenido() {
		return new Audio(null, titulo, autor, descripcion, ano, idioma, soporte, false, null, false, imagen, duracion);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(duracion);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AudioModel other = (AudioModel) obj;
		return super.equals(obj) && Objects.equals(duracion, other.duracion);
	}
	
}
