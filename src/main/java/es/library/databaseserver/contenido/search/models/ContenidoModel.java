package es.library.databaseserver.contenido.search.models;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.types.AbstractContenido;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;
import es.library.databaseserver.contenido.types.Video;

public class ContenidoModel implements AbstractContenido {

	protected Type type;
	protected String titulo;
	protected String autor;
	protected String descripcion;
	protected int ano;
	protected String idioma;
	protected Soporte soporte;
	protected List<Long> ids;
	protected URL imagen;

	protected ContenidoModel(Type type, String titulo, String autor, String descripcion, int ano, String idioma, Soporte soporte, URL imagen) {
		this.type = type;
		this.titulo = titulo;
		this.autor = autor;
		this.descripcion = descripcion;
		this.ano = ano;
		this.idioma = idioma;
		this.soporte = soporte;
		this.imagen = imagen;
	}	
	
	public List<Long> getIds() {return ids;}

	public void setIds(List<Long> ids) {this.ids = ids;}

	public String getTitulo() {return titulo;}

	public void setTitulo(String titulo) {this.titulo = titulo;}

	public String getAutor() {return autor;}

	public void setAutor(String autor) {this.autor = autor;}

	public String getDescripcion() {return descripcion;}

	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

	public int getAno() {return ano;}

	public void setAno(int ano) {this.ano = ano;}

	public String getIdioma() {return idioma;}

	public void setIdioma(String idioma) {this.idioma = idioma;}

	public Soporte getSoporte() {return soporte;}

	public void setSoporte(Soporte soporte) {this.soporte = soporte;}

	public URL getImagen() {return imagen;}
	public void setImagen(URL imagen) {this.imagen = imagen;}
	
	public Type getType() {return type;}
	public void setType(Type type) {this.type = type;}
	
	@Override
	public int hashCode() {
		return Objects.hash(ano, autor, descripcion, idioma, soporte, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContenidoModel other = (ContenidoModel) obj;
		return ano == other.ano && Objects.equals(autor, other.autor) && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(idioma, other.idioma) && soporte == other.soporte
				&& Objects.equals(titulo, other.titulo) && Objects.equals(imagen, other.imagen);
	}

	public static ContenidoModel ofContenido(Contenido c) {
		
		return switch (Type.ofContenido(c)) {
		case libro -> new LibroModel(
				c.getTitulo(), 
				c.getAutor(), 
				c.getDescripcion(),
				c.getAno(), 
				c.getIdioma(), 
				c.getSoporte(),
				c.getImagen(), 
				((Libro)c).getISBN(), ((Libro)c).getPaginas(), ((Libro)c).getEditorial());
		case video -> new VideoModel(c.getTitulo(), 
				c.getAutor(), 
				c.getDescripcion(),
				c.getAno(), 
				c.getIdioma(), 
				c.getSoporte(),
				c.getImagen(),
				((Video)c).getDuracion(),
				((Video)c).getEdadRecomendada(),
				((Video)c).getCalidad());
		case audio -> new AudioModel(c.getTitulo(), 
				c.getAutor(), 
				c.getDescripcion(),
				c.getAno(), 
				c.getIdioma(), 
				c.getSoporte(),
				c.getImagen(),
				((Audio)c).getDuracion());
		};
	}
	
	
	@Override
	public String toString() {
		return "ContenidoModel [titulo=" + titulo + ", autor=" + autor + ", descripcion=" + descripcion + ", ano=" + ano
				+ ", idioma=" + idioma + ", soporte=" + soporte + "]";
	}

	public Contenido toContenido() {
		return new Contenido(null, titulo, autor, descripcion, ano, idioma, soporte, false, null, false, imagen);
	}
	
	public enum Type{
		audio,video,libro;
		
		public static Type ofContenido(Contenido c) {
			Type tipo = null;
			if(c instanceof Video) {
				tipo = Type.video;
			}
			else if (c instanceof Audio) {
				tipo = Type.audio;
			}
			else if (c instanceof Libro) {
				tipo = Type.libro;
			}
			return tipo;
		}
	}
}
