package es.library.databaseserver.contenido.model;

public class ContenidoModelSet {

	private final ContenidoModel contenido;
	private final DetallesAudiovisualModel audiovisual;
	private final DetallesLibroModel libro;
	
	public ContenidoModelSet(ContenidoModel contenido, DetallesAudiovisualModel audiovisual, DetallesLibroModel libro) {
		this.contenido = contenido;
		this.audiovisual = audiovisual;
		this.libro = libro;
	}
	
	public ContenidoModel getContenido() {return contenido;}	
	public DetallesAudiovisualModel getAudiovisual() {return audiovisual;}	
	public DetallesLibroModel getLibro() {return libro;}
}
