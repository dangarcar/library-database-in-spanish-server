package es.library.databaseserver.contenido.dto;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Soporte;

public class Libros extends ContenidoDTO{
	
	private String ISBN;
	private Integer paginas;
	private String editorial;
	
	public Libros(Long iD, String titulo, String autor, String descripcion, Integer ano, String idioma, Soporte soporte,
			Boolean prestable, Integer diasDePrestamo, Boolean disponible, LocalDate fechaDisponibilidad, String iSBN,
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
