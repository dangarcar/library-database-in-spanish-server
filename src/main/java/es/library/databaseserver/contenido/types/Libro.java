package es.library.databaseserver.contenido.types;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Contenido;

public class Libro extends Contenido{
	
	private String ISBN;
	private Integer paginas;
	private String editorial;
	
	public Libro(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			boolean prestable, Integer diasDePrestamo, boolean disponible, LocalDate fechaDisponibilidad, String iSBN,
			Integer paginas, String editorial) {
		super(iD, titulo, autor, descripcion, ano, idioma, soporte, prestable, diasDePrestamo, disponible,
				fechaDisponibilidad);
		ISBN = iSBN;
		this.paginas = paginas;
		this.editorial = editorial;
	}
	
	public String getISBN() {return ISBN;}
	public void setISBN(String iSBN) {ISBN = iSBN;}
	
	public Integer getPaginas() {return paginas;}
	public void setPaginas(Integer paginas) {this.paginas = paginas;}
	
	public String getEditorial() {return editorial;}
	public void setEditorial(String editorial) {this.editorial = editorial;}
	
	@Override
	public String toString() {
		return super.toString()+"\nLibros [ISBN=" + ISBN + ", paginas=" + paginas + ", editorial=" + editorial + "]";
	}
}
