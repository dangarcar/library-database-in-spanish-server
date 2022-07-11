package es.library.databaseserver.perfil.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.repository.PerfilRepository;

@Service
public class PerfilService {

	private final PerfilRepository perfilRepository;
	
	@Autowired
	public PerfilService(@Qualifier("fake") PerfilRepository perfilRepository) {
		this.perfilRepository = perfilRepository;
	}
	
	public List<Perfil> getAllPerfiles() throws SQLException {
		return perfilRepository.getAllPerfiles();
	}
	
	public Perfil getPerfilByDNI(int DNI) throws SQLException {
		return perfilRepository.getPerfilByDNI(DNI);
	}
	
	public void insertPerfil(Perfil perfil) throws SQLException {
		perfilRepository.insertPerfil(perfil);
	}
	
	public void deletePerfilByDNI(int DNI) throws SQLException {
		perfilRepository.deletePerfilByDNI(DNI);
	}
	
	public void updatePerfilByDNI(int DNI, Perfil perfil) throws SQLException {
		perfilRepository.updatePerfilByDNI(DNI, perfil);
	}
	
}
