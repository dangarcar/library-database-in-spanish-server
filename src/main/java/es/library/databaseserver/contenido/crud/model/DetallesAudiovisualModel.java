package es.library.databaseserver.contenido.crud.model;

import java.util.Objects;

public class DetallesAudiovisualModel {

	private Long ID;
	private Double duracion;
	private boolean isVideo;
	private Integer edadRecomendada;
	private Integer calidad;
	
	public DetallesAudiovisualModel(Long iD, Double duracion, boolean isVideo, Integer edadRecomendada,
			Integer calidad) {
		ID = iD;
		this.duracion = duracion;
		this.isVideo = isVideo;
		this.edadRecomendada = edadRecomendada;
		this.calidad = calidad;
	}
	
	public Long getID() {return ID;}
	public void setID(Long iD) {ID = iD;}
	
	public Double getDuracion() {return duracion;}
	public void setDuracion(Double duracion) {this.duracion = duracion;}
	
	public boolean getIsVideo() {return isVideo;}
	public void setIsVideo(boolean isVideo) {this.isVideo = isVideo;}
	
	public Integer getEdadRecomendada() {return edadRecomendada;}
	public void setEdadRecomendada(Integer edadRecomendada) {this.edadRecomendada = edadRecomendada;}
	
	public Integer getCalidad() {return calidad;}
	public void setCalidad(Integer calidad) {this.calidad = calidad;}

	@Override
	public int hashCode() {
		return Objects.hash(ID, calidad, duracion, edadRecomendada, isVideo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetallesAudiovisualModel other = (DetallesAudiovisualModel) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(calidad, other.calidad)
				&& Objects.equals(duracion, other.duracion) && Objects.equals(edadRecomendada, other.edadRecomendada)
				&& Objects.equals(isVideo, other.isVideo);
	}

	
	@Override
	public String toString() {
		return "DetallesAudiovisualModel [ID=" + ID + ", duracion=" + duracion + ", isVideo=" + isVideo
				+ ", edadRecomendada=" + edadRecomendada + ", calidad=" + calidad + "]";
	}
	
	
	
}
