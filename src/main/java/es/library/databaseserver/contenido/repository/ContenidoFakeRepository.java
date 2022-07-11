package es.library.databaseserver.contenido.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Contenido;

@Repository("contenidoFake")
public class ContenidoFakeRepository implements ContenidoRepository {
	
	private final List<Contenido> contenidosList = new ArrayList<>();
	
	@Override
	public List<Contenido> getAllContenidos() throws SQLException {
		return contenidosList;
	}

	@Override
	public Contenido getContenidoByID(Long ID) throws SQLException {
		return contenidosList.stream().filter(p -> p.getID().equals(ID)).collect(Collectors.toList()).get(0);
	}

	@Override
	public void insertContenido(Contenido contenido) throws SQLException {
		contenidosList.add(contenido);
	}

	@Override
	public void deleteContenidoByID(Long ID) throws SQLException {
		contenidosList.remove(getContenidoByID(ID));
	}

	@Override
	public void updateContenidoByID(Long ID, Contenido contenido) throws SQLException {
		int index = -1;
		
		for(var c:contenidosList) {
			if(ID.equals(contenido.getID())) {
				index = contenidosList.indexOf(c);
				break;
			}
		}
		
		if(index != -1) contenidosList.set(index, contenido);
	}
    
}