package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;
import java.util.Objects;

import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.model.ContenidoModel;

public abstract class ContenidoDTO{
	
	private Long ID;
	private String titulo;
	private String autor;
	private String descripcion;
	private Integer ano;
	private String idioma;
	private Soporte soporte;
	private Boolean prestable;
	private Integer diasDePrestamo;
	private Boolean disponible;
	private LocalDate fechaDisponibilidad;
	
	public ContenidoDTO(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma,
			Soporte soporte, Boolean prestable, Integer diasDePrestamo, Boolean disponible,
			LocalDate fechaDisponibilidad) {
		ID = iD;
		this.titulo = titulo;
		this.autor = autor;
		this.descripcion = descripcion;
		this.ano = ano;
		this.idioma = idioma;
		this.soporte = soporte;
		this.prestable = prestable;
		this.diasDePrestamo = diasDePrestamo;
		this.disponible = disponible;
		this.fechaDisponibilidad = fechaDisponibilidad;
	}
	
	//TIPICOS GETTERS Y SETTERS DE CLASE JAVA
	public Long getID() {return ID;}
	public void setID(Long iD) {ID = iD;}
	
	public String getTitulo() {return titulo;}
	public void setTitulo(String titulo) {this.titulo = titulo;}
	
	public String getAutor() {return autor;}
	public void setAutor(String autor) {this.autor = autor;}
	
	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	
	public Integer getAno() {return ano;}
	public void setAno(Integer ano) {this.ano = ano;}
	
	public String getIdioma() {return idioma;}
	public void setIdioma(String idioma) {this.idioma = idioma;}
	
	public Soporte getSoporte() {return soporte;}
	public void setSoporte(Soporte soporte) {this.soporte = soporte;}
	
	public Boolean getPrestable() {return prestable;}
	public void setPrestable(Boolean prestable) {this.prestable = prestable;}
	
	public Integer getDiasDePrestamo() {return diasDePrestamo;}
	public void setDiasDePrestamo(Integer diasDePrestamo) {this.diasDePrestamo = diasDePrestamo;}
	
	public Boolean getDisponible() {return disponible;}
	public void setDisponible(Boolean disponible) {this.disponible = disponible;}
	
	public LocalDate getFechaDisponibilidad() {return fechaDisponibilidad;}
	public void setFechaDisponibilidad(LocalDate fechaDisponibilidad) {this.fechaDisponibilidad = fechaDisponibilidad;}

	
	//TIPICOS hashCode(), equals() y toString() DE UNA CLASE JAVA
	@Override
	public int hashCode() {
		return Objects.hash(ID, autor, soporte, titulo);
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
		return Objects.equals(ID, other.getID()) && Objects.equals(autor, other.getAutor()) && soporte == other.getSoporte()
				&& Objects.equals(titulo, other.getTitulo());
	}
	
	@Override
	public String toString() {
		return "Contenido [ID=" + ID + ", titulo=" + titulo + ", autor=" + autor + ", ano=" + ano + ", idioma=" + idioma
				+ ", soporte=" + soporte + ", prestable=" + prestable + ", diasDePrestamo=" + diasDePrestamo
				+ ", disponible=" + disponible + ", fechaDisponibilidad=" + fechaDisponibilidad + "]";
	}
	
}
