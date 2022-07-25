package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;

public class Libros extends Contenido{
	
	private String ISBN;
	private int paginas;
	private String editorial;
	private Long IDLibro;
	
	public Libros(Long iD, String titulo, String autor, String descripcion, int ano, String idioma, Soporte soporte,
			boolean prestable, int diasDePrestamo, boolean disponible, LocalDate fechaDisponibilidad, String iSBN,
			int paginas, String editorial,long IDLibro) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad,IDLibro,null);
		ISBN = iSBN;
		this.paginas = paginas;
		this.editorial = editorial;
		this.IDLibro = IDLibro;
	}
	
	public String getISBN() {return ISBN;}
	public void setISBN(String iSBN) {ISBN = iSBN;}
	
	public Integer getPaginas() {return paginas;}
	public void setPaginas(Integer paginas) {this.paginas = paginas;}
	
	public String getEditorial() {return editorial;}
	public void setEditorial(String editorial) {this.editorial = editorial;}

	public Long getIDLibro() {return IDLibro;}
	public void setIDLibro(Long iDLibro) {IDLibro = iDLibro;}
	
	@Override
	public String toString() {
		return super.toString()+"\nLibros [ISBN=" + ISBN + ", paginas=" + paginas + ", editorial=" + editorial + "]";
	}
}
