package es.library.databaseserver.perfil;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Objects;


public class Perfil{
	private final char[] letrasDNI = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento = null;
	private String direccionDeCasa;
	private String correoElectronico;
	private Integer DNI;
	private ArrayList<Integer> contenidosEnPrestamo = new ArrayList<Integer>();
	private Integer edad;

	
	public Perfil(String nombre, String apellido, LocalDate fechaNacimiento, Integer dni, String direccionDeCasa, String correoElectronico) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.DNI = dni;
		this.direccionDeCasa = direccionDeCasa;
		this.correoElectronico = correoElectronico;
	}
	
	//TIPICOS GETTERS Y SETTERS DE UNA CLASE DE JAVA
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}

	public String getApellido() {return apellido;}
	public void setApellido(String apellido) {this.apellido = apellido;}

	public LocalDate getFechaNacimiento() {return fechaNacimiento;}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

	public String getDireccionDeCasa() {return direccionDeCasa;}
	public void setDireccionDeCasa(String direccionDeCasa) {this.direccionDeCasa = direccionDeCasa;}

	public String getCorreoElectronico() {return correoElectronico;}
	public void setCorreoElectronico(String correoElectronico) {this.correoElectronico = correoElectronico;}

	public Integer getDNI() {return DNI;}
	public void setDNI(int dNI) {DNI = dNI;}

	public ArrayList<Integer> getContenidosEnPrestamo() {return contenidosEnPrestamo;}
	public void setContenidosEnPrestamo(ArrayList<Integer> contenidosEnPrestamo) {this.contenidosEnPrestamo = contenidosEnPrestamo;}
	
	public void setEdad(int edad) {this.edad = edad;}

	
	//TIPICOS hashCode(), equals() y toString() DE UNA CLASE JAVA	
	@Override
	public int hashCode() {
		return Objects.hash(DNI, nombre, apellido);
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
		return Objects.equals(nombre, other.nombre) && DNI == other.DNI && Objects.equals(apellido, other.apellido);
	}
	
	@Override
	public String toString() {
		return "Perfil [nombre=" + nombre +" "+ apellido + ", fechaNacimiento=" + fechaNacimiento
				+ ", correoElectronico=" + correoElectronico + ", DNI=" + DNI + "]";
	}

	//GETTERS ESPECIALES
	public Character getLetraDNI() {
		return this.letrasDNI[DNI % 23];
	}
	
	public Integer getEdad() {
		this.edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
		return edad;
	}
}

