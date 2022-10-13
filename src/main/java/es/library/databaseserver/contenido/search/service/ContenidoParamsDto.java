package es.library.databaseserver.contenido.search.service;

import java.util.Objects;

import es.library.databaseserver.contenido.Contenido.Soporte;

public class ContenidoParamsDto {

	private String query;
	private String titulo;
	private String autor;
	private Integer minAno;
	private Integer maxAno;
	private String idioma;
	private Soporte soporte;
	private Integer minPaginas;
	private Integer maxPaginas;
	private String editorial;
	private String isbn;
	private Integer minEdad;
	private Integer maxEdad;
	private Double minDuracion;
	private Double maxDuracion;
	private Integer minCalidad;
	private Integer maxCalidad;
	private String type;
	private Boolean disponible;
	private Boolean unique;
	private Boolean prestable;
	
	public ContenidoParamsDto() {}

	public ContenidoParamsDto(String query, String titulo, String autor, Integer minAno, Integer maxAno, String idioma,
			Soporte soporte, Integer minPaginas, Integer maxPaginas, String editorial, String isbn, Integer minEdad,
			Integer maxEdad, Double minDuracion, Double maxDuracion, Integer minCalidad, Integer maxCalidad,
			String type, Boolean disponible, Boolean unique, Boolean prestable) {
		this.query = query;
		this.titulo = titulo;
		this.autor = autor;
		this.minAno = minAno;
		this.maxAno = maxAno;
		this.idioma = idioma;
		this.soporte = soporte;
		this.minPaginas = minPaginas;
		this.maxPaginas = maxPaginas;
		this.editorial = editorial;
		this.isbn = isbn;
		this.minEdad = minEdad;
		this.maxEdad = maxEdad;
		this.minDuracion = minDuracion;
		this.maxDuracion = maxDuracion;
		this.minCalidad = minCalidad;
		this.maxCalidad = maxCalidad;
		this.type = type;
		this.disponible = disponible;
		this.unique = unique;
		this.prestable = prestable;
	}

	public String getQuery() {return query;}
	public void setQuery(String query) {this.query = query;}

	public String getTitulo() {return titulo;}
	public void setTitulo(String titulo) {this.titulo = titulo;}

	public String getAutor() {return autor;}
	public void setAutor(String autor) {this.autor = autor;}

	public Integer getMinAno() {return minAno;}
	public void setMinAno(Integer minAno) {this.minAno = minAno;}

	public Integer getMaxAno() {return maxAno;}
	public void setMaxAno(Integer maxAno) {this.maxAno = maxAno;}

	public String getIdioma() {return idioma;}
	public void setIdioma(String idioma) {this.idioma = idioma;}

	public Soporte getSoporte() {return soporte;}
	public void setSoporte(Soporte soporte) {this.soporte = soporte;}

	public Integer getMinPaginas() {return minPaginas;}
	public void setMinPaginas(Integer minPaginas) {this.minPaginas = minPaginas;}

	public Integer getMaxPaginas() {return maxPaginas;}
	public void setMaxPaginas(Integer maxPaginas) {this.maxPaginas = maxPaginas;}

	public String getEditorial() {return editorial;}
	public void setEditorial(String editorial) {this.editorial = editorial;}

	public String getIsbn() {return isbn;}
	public void setIsbn(String isbn) {this.isbn = isbn;}

	public Integer getMinEdad() {return minEdad;}
	public void setMinEdad(Integer minEdad) {this.minEdad = minEdad;}

	public Integer getMaxEdad() {return maxEdad;}
	public void setMaxEdad(Integer maxEdad) {this.maxEdad = maxEdad;}

	public Double getMinDuracion() {return minDuracion;}
	public void setMinDuracion(Double minDuracion) {this.minDuracion = minDuracion;}

	public Double getMaxDuracion() {return maxDuracion;}
	public void setMaxDuracion(Double maxDuracion) {this.maxDuracion = maxDuracion;}

	public Integer getMinCalidad() {return minCalidad;}
	public void setMinCalidad(Integer minCalidad) {this.minCalidad = minCalidad;}

	public Integer getMaxCalidad() {return maxCalidad;}
	public void setMaxCalidad(Integer maxCalidad) {this.maxCalidad = maxCalidad;}

	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	public Boolean getDisponible() {return disponible;}
	public void setDisponible(Boolean disponible) {this.disponible = disponible;}

	public Boolean getUnique() {return unique;}
	public void setUnique(Boolean unique) {this.unique = unique;}

	public Boolean getPrestable() {return prestable;}
	public void setPrestable(Boolean prestable) {this.prestable = prestable;}

	@Override
	public String toString() {
		return "ContenidoParamsDto [query=" + query + ", titulo=" + titulo + ", autor=" + autor + ", minAno=" + minAno
				+ ", maxAno=" + maxAno + ", idioma=" + idioma + ", soporte=" + soporte + ", minPaginas=" + minPaginas
				+ ", maxPaginas=" + maxPaginas + ", editorial=" + editorial + ", isbn=" + isbn + ", minEdad=" + minEdad
				+ ", maxEdad=" + maxEdad + ", minDuracion=" + minDuracion + ", maxDuracion=" + maxDuracion
				+ ", minCalidad=" + minCalidad + ", maxCalidad=" + maxCalidad + ", type=" + type + ", disponible="
				+ disponible + ", unique=" + unique + ", prestable=" + prestable + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(autor, disponible, editorial, idioma, isbn, maxAno, maxCalidad, maxDuracion, maxEdad,
				maxPaginas, minAno, minCalidad, minDuracion, minEdad, minPaginas, prestable, query, soporte, titulo,
				type, unique);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContenidoParamsDto other = (ContenidoParamsDto) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(disponible, other.disponible)
				&& Objects.equals(editorial, other.editorial) && Objects.equals(idioma, other.idioma)
				&& Objects.equals(isbn, other.isbn) && Objects.equals(maxAno, other.maxAno)
				&& Objects.equals(maxCalidad, other.maxCalidad) && Objects.equals(maxDuracion, other.maxDuracion)
				&& Objects.equals(maxEdad, other.maxEdad) && Objects.equals(maxPaginas, other.maxPaginas)
				&& Objects.equals(minAno, other.minAno) && Objects.equals(minCalidad, other.minCalidad)
				&& Objects.equals(minDuracion, other.minDuracion) && Objects.equals(minEdad, other.minEdad)
				&& Objects.equals(minPaginas, other.minPaginas) && Objects.equals(prestable, other.prestable)
				&& Objects.equals(query, other.query) && soporte == other.soporte
				&& Objects.equals(titulo, other.titulo) && Objects.equals(type, other.type)
				&& Objects.equals(unique, other.unique);
	}

}
