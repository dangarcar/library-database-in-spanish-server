package es.library.databaseserver.contenido.types;

import java.util.List;
import java.util.Objects;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;

public class ContenidoModel implements AbstractContenido{

	private String titulo;
	private String autor;
	private String descripcion;
	private int ano;
	private String idioma;
	private Soporte soporte;
	private List<Long> ids;

	private ContenidoModel(String titulo, String autor, String descripcion, int ano, String idioma, Soporte soporte) {
		this.titulo = titulo;
		this.autor = autor;
		this.descripcion = descripcion;
		this.ano = ano;
		this.idioma = idioma;
		this.soporte = soporte;
	}

	
	
	public List<Long> getIds() {
		return ids;
	}



	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Soporte getSoporte() {
		return soporte;
	}

	public void setSoporte(Soporte soporte) {
		this.soporte = soporte;
	}

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
				&& Objects.equals(titulo, other.titulo);
	}

	public static ContenidoModel ofContenido(Contenido c) {
		return new ContenidoModel(
				c.getTitulo(), 
				c.getAutor(), 
				c.getDescripcion(), 
				c.getAno(), 
				c.getIdioma(), 
				c.getSoporte()
			);
	}
	
	
	@Override
	public String toString() {
		return "ContenidoModel [titulo=" + titulo + ", autor=" + autor + ", descripcion=" + descripcion + ", ano=" + ano
				+ ", idioma=" + idioma + ", soporte=" + soporte + "]";
	}

	public Contenido toContenido() {
		return new Contenido(null, titulo, autor, descripcion, ano, idioma, soporte, false, null, false, null);
	}
}
