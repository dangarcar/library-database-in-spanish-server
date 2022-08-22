package es.library.databaseserver.perfil.crud.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.dao.PerfilDAO;
import es.library.databaseserver.perfil.crud.model.PerfilValidator;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.perfil.exceptions.EmailAlreadyExistPerfilException;
import es.library.databaseserver.perfil.exceptions.IllegalPerfilException;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;
import es.library.databaseserver.perfil.search.dao.PerfilSearchDAO;

@Service
public class PerfilServiceImpl implements PerfilService{

	@Autowired
	private PerfilDAO perfilDAO;
	
	@Autowired
	private PerfilValidator validator;
	
	@Autowired
	private PerfilSearchDAO perfilSearch;
	
	@Autowired
	private PasswordEncoder encoder;
	
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
	public Perfil insertPerfil(Perfil perfil) throws IllegalPerfilException, EmailAlreadyExistPerfilException {
		//Compruebo si el perfil es correcto
		validator.validatePerfilCorrect(perfil);
		this.validateEmailExistInDB(perfil, perfil.getID());
		
		perfil.setContrasena(encoder.encode(perfil.getContrasena()));
		
		return perfilDAO.insertPerfil(perfil);
	}

	@Override
	public void deletePerfilByID(Long id) throws PerfilNotFoundException {
		perfilDAO.deletePerfilByID(id);
	}

	@Override
	public Perfil updatePerfilByID(Long id, Perfil perfil) throws IllegalPerfilException, PerfilNotFoundException, EmailAlreadyExistPerfilException {
		//Compruebo si el perfil es correcto
		validator.validatePerfilCorrectUpdating(perfil);
		if(perfil.getCorreoElectronico()!=null) this.validateEmailExistInDB(perfil,id);
		
		String oldPassword = getPerfilByID(id).getContrasena();
		if(!encoder.matches(perfil.getContrasena(),oldPassword)) 
			perfil.setContrasena(encoder.encode(perfil.getContrasena()));
		else perfil.setContrasena(oldPassword);
		
		return perfilDAO.updatePerfilByID(id, perfil);
	}

	@Override
	public List<Perfil> idListToPerfilList(List<Long> ids){
		return ids.stream()
				.map(id -> getPerfilByID(id))
				.collect(Collectors.toList());
	}

	@Override
	public void setRole(long id, Roles roles) throws PerfilNotFoundException{
		Perfil perfil = getPerfilByID(id);
		
		perfil.setRole(roles);
		
		updatePerfilByID(id, perfil);
	}
	
	private void validateEmailExistInDB(Perfil perfil, Long id) throws EmailAlreadyExistPerfilException {
		var perfiles = perfilSearch.getPerfilesByEmail(perfil.getCorreoElectronico());
		if(!perfiles.isEmpty()) {
			if(!perfiles.contains(id))
				throw new EmailAlreadyExistPerfilException(
						"El correo electrónico " + perfil.getCorreoElectronico() + " ya existe en el sistema");
		}	
	}
	
}
