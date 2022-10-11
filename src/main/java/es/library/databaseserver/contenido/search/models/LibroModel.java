package es.library.databaseserver.contenido.search.models;

import java.net.URL;
import java.util.Objects;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.types.Libro;

public class LibroModel extends ContenidoModel {

	private String ISBN;
	private Integer paginas;
	private String editorial;
	
	public LibroModel(String titulo, String autor, String descripcion, int ano, String idioma,
			Soporte soporte, URL imagen, String iSBN, Integer paginas, String editorial) {
		super(Type.libro, titulo, autor, descripcion, ano, idioma, soporte, imagen);
		ISBN = iSBN;
		this.paginas = paginas;
		this.editorial = editorial;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	@Override
	public String toString() {
		return super.toString()+"\nLibroModel [ISBN=" + ISBN + ", paginas=" + paginas + ", editorial=" + editorial + "]";
	}
	
	@Override
	public Contenido toContenido() {
		return new Libro(null, titulo, autor, descripcion, ano, idioma, soporte, false, null, false, imagen, ISBN, paginas, editorial);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(ISBN, editorial, paginas);
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
		LibroModel other = (LibroModel) obj;
		return Objects.equals(ISBN, other.ISBN) && Objects.equals(editorial, other.editorial)
				&& Objects.equals(paginas, other.paginas) && super.equals(obj);
	}
	
}
