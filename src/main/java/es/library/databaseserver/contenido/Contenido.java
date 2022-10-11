package es.library.databaseserver.contenido;

import java.net.URL;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import es.library.databaseserver.contenido.types.AbstractContenido;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;
import es.library.databaseserver.contenido.types.Video;

@JsonInclude(Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Audio.class, name = "audio"),
        @JsonSubTypes.Type(value = Video.class, name = "video"),
        @JsonSubTypes.Type(value = Libro.class, name = "libro")
})
public class Contenido implements AbstractContenido{
	
	private Long ID;
	private String titulo;
	private String autor;
	private String descripcion;
	private Integer ano;
	private String idioma;
	private Soporte soporte;
	private boolean prestable;
	private Integer diasDePrestamo;
	private boolean disponible;
//	private LocalDate fechaDisponibilidad;
	private Long IDLibro;
	private Long IDAudiovisual;
	private URL imagen;
	
	/**
	 * Enum que define los soportes en los que pueden estar los contenidos de la biblioteca
	 * @author Daniel García
	 *
	 */
	public enum Soporte {
		FISICO(false,false),
		E_BOOK(false,false),
		CD(true,true),
		DVD(true,true),
		BLURAY(true,true),
		VINILO(true,false),
		CASETE(true,false);
		
		private boolean multimedia;
		private boolean audio;
		
		Soporte(boolean audio,boolean multimedia){
			this.multimedia = multimedia;
			this.audio = audio;
		}
		
		public boolean isMultimedia() {
			return multimedia;
		}
		
		public boolean isAudio() {
			return audio;
		}
	}
	
	public Contenido(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma,
			Soporte soporte, boolean prestable, Integer diasDePrestamo, boolean disponible, URL imagen/*,
			LocalDate fechaDisponibilidad*/) {
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
		this.imagen = imagen;
//		this.fechaDisponibilidad = fechaDisponibilidad;
	}

	//TIPICOS GETTERS Y SETTERS DE UNA CLASE JAVA
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
	
	public boolean getPrestable() {return prestable;}
	public void setPrestable(boolean prestable) {this.prestable = prestable;}
	
	public Integer getDiasDePrestamo() {return diasDePrestamo;}
	public void setDiasDePrestamo(Integer diasDePrestamo) {this.diasDePrestamo = diasDePrestamo;}
	
	public boolean getDisponible() {return disponible;}
	public void setDisponible(boolean disponible) {this.disponible = disponible;}
	
	public URL getImagen() {return imagen;}
	public void setImagen(URL imagen) {this.imagen = imagen;}
	
//	public LocalDate getFechaDisponibilidad() {return fechaDisponibilidad;}
//	public void setFechaDisponibilidad(LocalDate fechaDisponibilidad) {this.fechaDisponibilidad = fechaDisponibilidad;}
	
	@JsonIgnore
	public Long getIDAudiovisual() {return IDAudiovisual;}
	@JsonIgnore
	public void setIDAudiovisual(Long iDAudiovisual) {IDAudiovisual = iDAudiovisual;}
	
	@JsonIgnore
	public Long getIDLibro() {return IDLibro;}
	@JsonIgnore
	public void setIDLibro(Long iDLibro) {IDLibro = iDLibro;}
	
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
		Contenido other = (Contenido) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(IDAudiovisual, other.IDAudiovisual)
				&& Objects.equals(IDLibro, other.IDLibro) && ano == other.ano && Objects.equals(autor, other.autor)
				&& Objects.equals(descripcion, other.descripcion) && diasDePrestamo == other.diasDePrestamo
				&& disponible == other.disponible /*&& Objects.equals(fechaDisponibilidad, other.fechaDisponibilidad)*/
				&& Objects.equals(idioma, other.idioma) && prestable == other.prestable && soporte == other.soporte
				&& Objects.equals(titulo, other.titulo);
	}
	
	@Override
	public String toString() {
		return "Contenido [ID=" + ID + ", titulo=" + titulo + ", autor=" + autor + ", ano=" + ano + ", idioma=" + idioma
				+ ", soporte=" + soporte + ", prestable=" + prestable + ", diasDePrestamo=" + diasDePrestamo
				+ ", disponible=" + disponible + /*", fechaDisponibilidad=" + fechaDisponibilidad +*/ "]";
	}
}
