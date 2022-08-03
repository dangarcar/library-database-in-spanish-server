package es.library.databaseserver.contenido.crud;

import java.time.LocalDate;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.IllegalContenidoException;

public class Libros extends Contenido{
	
	private String ISBN;
	private int paginas;
	private String editorial;
	
	public Libros(Long iD, String titulo, String autor, String descripcion, int ano, String idioma, Soporte soporte,
			boolean prestable, int diasDePrestamo, boolean disponible, LocalDate fechaDisponibilidad, String iSBN,
			int paginas, String editorial) {
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
	
	public void checkIsCorrect() throws IllegalContenidoException{
		super.checkIsCorrect();
		
		if(getSoporte().isAudio() || getSoporte().isMultimedia()) throw new IllegalContenidoException("El libro debe estar en plataformas compatibles");
		
		if(ISBN == null) throw new IllegalContenidoException("El ISBN de los libros no debe ser nulo", new NullPointerException());
		
		if(paginas <= 0) throw new IllegalContenidoException("Un libro debe de tener alguna pagina");
	}
}
