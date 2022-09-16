package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import java.util.List;
import java.util.Map;

import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public interface PerfilSearchSnippets {

	MultiValueMap<String, String> GET_PERFILES_PARAMS_MAP = new LinkedMultiValueMap<>(Map.ofEntries(
			Map.entry("q",				List.of("dfhjk")),
			Map.entry("nombre", 		List.of("dfsg")),
			Map.entry("email", 			List.of("dfsg@hkjs.xc")),
			Map.entry("fromNacimiento", List.of("0002-09-11")),
			Map.entry("toNacimiento", 	List.of("0002-08-11")),
			Map.entry("role",			List.of("ROLE_ADMIN"))
		));
	
	RequestParametersSnippet GET_PERFILES_PARAMS_SNIPPET = requestParameters(
			parameterWithName("q")
				.description("Las palabras claves con las que se buscarán los perfiles, separados por espacios").optional(),
			parameterWithName("nombre")
				.description("El nombre completo del perfil").optional(),
			parameterWithName("email")
				.description("El correo electrónico del perfil").optional(),
			parameterWithName("fromNacimiento")
				.description("La fecha de nacimiento del perfil (mínimo)").optional(),
			parameterWithName("toNacimiento")
				.description("La fecha de nacimiento del perfil (máximo)").optional(),
			parameterWithName("role")
				.description("El rol del perfil en la biblioteca, precedido de \"ROLE_\"").optional()
		);
	
	PathParametersSnippet USERNAME_PATH_PARAM = pathParameters(
			parameterWithName("username")
			.description("El nombre de usuario del perfil en la base de datos, un correo electrónico"));
	
}
