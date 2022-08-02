package es.library.databaseserver.prestamos;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Prestamo {

	private Long ID;
	private Long IDPerfil;
	private Long IDContenido;
	private LocalDateTime fechaHoraPrestamo;
	private LocalDateTime fechaHoraDevolucion;
	private int diasdePrestamo;
	private boolean devuelto;
	
	public Prestamo(Long iD, Long iDContenido, Long iDPerfil, LocalDateTime fechaHoraPrestamo, LocalDateTime fechaHoraDevolucion,
			int diasdePrestamo, boolean devuelto) {
		super();
		ID = iD;
		IDPerfil = iDPerfil;
		IDContenido = iDContenido;
		this.fechaHoraPrestamo = fechaHoraPrestamo;
		this.fechaHoraDevolucion = fechaHoraDevolucion;
		this.diasdePrestamo = diasdePrestamo;
		this.devuelto = devuelto;
	}
	
	public Long getID() {return ID;}
	public void setID(Long iD) {ID = iD;}
	
	public Long getIDPerfil() {return IDPerfil;}
	public void setIDPerfil(Long iDPerfil) {IDPerfil = iDPerfil;}
	
	public Long getIDContenido() {return IDContenido;}
	public void setIDContenido(Long iDContenido) {IDContenido = iDContenido;}
	
	public LocalDateTime getFechaHoraPrestamo() {return fechaHoraPrestamo;}
	public void setFechaHoraPrestamo(LocalDateTime fechaHoraPrestamo) {this.fechaHoraPrestamo = fechaHoraPrestamo;}
	
	public LocalDateTime getFechaHoraDevolucion() {return fechaHoraDevolucion;}
	public void setFechaHoraDevolucion(LocalDateTime fechaHoraDevolucion) {this.fechaHoraDevolucion = fechaHoraDevolucion;}
	
	public int getDiasdePrestamo() {return diasdePrestamo;}
	public void setDiasdePrestamo(int diasdePrestamo) {this.diasdePrestamo = diasdePrestamo;}
	
	public boolean isDevuelto() {return devuelto;}
	public void setDevuelto(boolean devuelto) {this.devuelto = devuelto;}
	
	@Override
	public int hashCode() {
		return Objects.hash(ID, IDContenido, IDPerfil, devuelto, diasdePrestamo, fechaHoraDevolucion,
				fechaHoraPrestamo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(IDContenido, other.IDContenido)
				&& Objects.equals(IDPerfil, other.IDPerfil) && devuelto == other.devuelto
				&& diasdePrestamo == other.diasdePrestamo
				&& Objects.equals(fechaHoraDevolucion, other.fechaHoraDevolucion)
				&& Objects.equals(fechaHoraPrestamo, other.fechaHoraPrestamo);
	}
	
	@Override
	public String toString() {
		return "Prestamo [ID=" + ID + ", IDPerfil=" + IDPerfil + ", IDContenido=" + IDContenido + ", fechaHoraPrestamo="
				+ fechaHoraPrestamo + ", fechaHoraDevolucion=" + fechaHoraDevolucion + ", diasdePrestamo="
				+ diasdePrestamo + ", devuelto=" + devuelto + "]";
	}
}
