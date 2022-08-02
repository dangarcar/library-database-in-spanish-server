package es.library.databaseserver.perfil;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Perfil {
	
	private Long ID;
	private String nombre;
	private LocalDate fechaNacimiento;
	private String correoElectronico;
	private String contrasena;
	private boolean admin;
	@JsonIgnore
	private int edad;
	
	public Perfil(Long iD, String nombre, LocalDate fechaNacimiento, @JsonProperty("email") String correoElectronico, @JsonProperty("password") String contrasena,
			boolean admin) {
		ID = iD;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.correoElectronico = correoElectronico;
		this.contrasena = contrasena;
		this.admin = admin;
	}
	
	public Long getID() {return ID;}
	public void setID(Long iD) {ID = iD;}
	
	public String getNombre() { return nombre;}
	public void setNombre(String nombre) { this.nombre = nombre;}
	
	public LocalDate getFechaNacimiento() { return fechaNacimiento;}
	public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento;}
	
	public String getCorreoElectronico() { return correoElectronico;}
	public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico;}
	
	public String getContrasena() { return contrasena;}
	@JsonIgnore
	public void setContrasena(String contrasena) { this.contrasena = contrasena;}
	
	public boolean isAdmin() { return admin;}
	public void setAdmin(boolean admin) { this.admin = admin;}
	
	@JsonIgnore
	public int getEdad() { return edad = Period.between(fechaNacimiento, LocalDate.now()).getYears(); }

	@Override
	public int hashCode() {
		return Objects.hash(ID, admin, contrasena, correoElectronico, fechaNacimiento, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perfil other = (Perfil) obj;
		return Objects.equals(ID, other.ID) && admin == other.admin && Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(correoElectronico, other.correoElectronico)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && Objects.equals(nombre, other.nombre);
	}


	@Override
	public String toString() {
		return "Contenido [ID=" + ID + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", correoElectronico=" + correoElectronico + ", contrasena=" + contrasena + ", admin=" + admin + "]";
	}
}
