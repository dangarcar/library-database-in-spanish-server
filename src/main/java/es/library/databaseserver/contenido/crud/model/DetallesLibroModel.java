package es.library.databaseserver.contenido.crud.model;

import java.util.Objects;

public class DetallesLibroModel {

	private Long ID;
	private String ISBN;
	private Integer paginas;
	private String editorial;
	
	public DetallesLibroModel(Long iD, String iSBN, Integer paginas, String editorial) {
		ID = iD;
		ISBN = iSBN;
		this.paginas = paginas;
		this.editorial = editorial;
	}
	
	public Long getID() {return ID;}
	public void setID(Long iD) {ID = iD;}
	
	public String getISBN() {return ISBN;}
	public void setISBN(String iSBN) {ISBN = iSBN;}
	
	public Integer getPaginas() {return paginas;}
	public void setPaginas(Integer paginas) {this.paginas = paginas;}
	
	public String getEditorial() {return editorial;}
	public void setEditorial(String editorial) {this.editorial = editorial;}

	@Override
	public int hashCode() {
		return Objects.hash(ID, ISBN, editorial, paginas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetallesLibroModel other = (DetallesLibroModel) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(ISBN, other.ISBN)
				&& Objects.equals(editorial, other.editorial) && Objects.equals(paginas, other.paginas);
	}

	
	@Override
	public String toString() {
		return "DetallesLibroModel [ID=" + ID + ", ISBN=" + ISBN + ", paginas=" + paginas + ", editorial=" + editorial
				+ "]";
	}
}
