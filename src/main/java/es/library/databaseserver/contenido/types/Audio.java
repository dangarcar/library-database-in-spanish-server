package es.library.databaseserver.contenido.types;

import java.net.URL;
import java.util.Objects;

import es.library.databaseserver.contenido.Contenido;

public class Audio extends Contenido{
	
	private Double duracion;

	public Audio(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			boolean prestable, Integer diasDePrestamo, boolean disponible, URL imagen, Double duracion) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible, imagen);
		this.duracion = duracion;
	}
	
	public Double getDuracion() {return duracion;}
	public void setDuracion(Double duracion) {this.duracion = duracion;}

	@Override
	public String toString() {
		return super.toString()+"\nAudio [duracion=" + duracion +"]";
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
		Audio other = (Audio) obj;
		return super.equals(obj) && Objects.equals(duracion, other.duracion);
	}
	
	
	
}
