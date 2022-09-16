package es.library.databaseserver.perfil.crud.model;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;

@JsonInclude(Include.NON_NULL)
public class PerfilResponse {
	
	private Long ID;
	private String nombre;
	private LocalDate fechaNacimiento;
	private String correoElectronico;
	private Roles role;
	
	public PerfilResponse(Long iD, String nombre, LocalDate fechaNacimiento, @JsonProperty("email") String correoElectronico, Roles role) {
		ID = iD;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.correoElectronico = correoElectronico;
		this.role = role;
	}
	
	public PerfilResponse(Perfil perfil) {
		this.ID = perfil.getID();
		this.nombre = perfil.getNombre();
		this.fechaNacimiento = perfil.getFechaNacimiento();
		this.correoElectronico = perfil.getCorreoElectronico();
		this.role = perfil.getRole();
	}
	
	public Long getID() {return ID;}
	public void setID(Long iD) {ID = iD;}
	
	public String getNombre() { return nombre;}
	public void setNombre(String nombre) { this.nombre = nombre;}
	
	public LocalDate getFechaNacimiento() { return fechaNacimiento;}
	public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento;}
	
	public String getCorreoElectronico() { return correoElectronico;}
	public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico;}
	
	public Roles getRole() { return role; }
	public void setRole(Roles role) { this.role = role; }

	@Override
	public int hashCode() {
		return Objects.hash(ID, correoElectronico, fechaNacimiento, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilResponse other = (PerfilResponse) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(correoElectronico, other.correoElectronico)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && Objects.equals(nombre, other.nombre);
	}


	@Override
	public String toString() {
		return "Contenido [ID=" + ID + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", correoElectronico=" + correoElectronico + ", role=" + role + "]";
	}

}