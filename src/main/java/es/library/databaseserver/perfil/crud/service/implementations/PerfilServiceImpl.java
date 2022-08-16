package es.library.databaseserver.perfil.crud.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.dao.PerfilDAO;
import es.library.databaseserver.perfil.crud.model.PerfilValidator;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.perfil.exceptions.IllegalPerfilException;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;

@Service
public class PerfilServiceImpl implements PerfilService{

	@Autowired
	private PerfilDAO perfilDAO;
	
	@Autowired
	private PerfilValidator validator;
	
	@Override
	public List<Perfil> getAllPerfiles() {
		return idListToPerfilList(perfilDAO.getAllPerfilesID());
	}

	@Override
	public Perfil getPerfilByID(Long id) throws PerfilNotFoundException {
		return perfilDAO.getPerfilByID(id)
				.orElseThrow(() -> new PerfilNotFoundException("El perfil con id "+id+" no existe en la base de datos"));
	}

	@Override
	public Perfil insertPerfil(Perfil perfil) throws IllegalPerfilException {
		//Compruebo si el perfil es correcto
		validator.validatePerfilCorrect(perfil);

		return perfilDAO.insertPerfil(perfil);
	}

	@Override
	public void deletePerfilByID(Long id) throws PerfilNotFoundException {
		perfilDAO.deletePerfilByID(id);
	}

	@Override
	public Perfil updatePerfilByID(Long id, Perfil perfil) throws IllegalPerfilException, PerfilNotFoundException {
		//Compruebo si el perfil es correcto
		validator.validatePerfilCorrect(perfil);
		
		return perfilDAO.updatePerfilByID(id, perfil);
	}

	@Override
	public List<Perfil> idListToPerfilList(List<Long> ids){
		return ids.stream()
				.map(id -> getPerfilByID(id))
				.collect(Collectors.toList());
	}

	@Override
	public void makePerfilAdmin(Long id) throws PerfilNotFoundException{
		Perfil perfil = getPerfilByID(id);
		
		perfil.setAdmin(true);
		
		updatePerfilByID(id, perfil);
	}

	@Override
	public void makeAdminPerfil(Long id) throws PerfilNotFoundException{
		Perfil perfil = getPerfilByID(id);
		
		perfil.setAdmin(false);
		
		updatePerfilByID(id, perfil);
	}
	
}
