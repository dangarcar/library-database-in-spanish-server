package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import java.util.Map;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;

public interface PerfilSnippets {

	Map<String, FieldDescriptor> PERFIL_FIELDS = Map.ofEntries(
			Map.entry("id", fieldWithPath("id")
					.description("El identificador del perfil en la base de datos")),
			Map.entry("nombre", fieldWithPath("nombre")
					.description("El nombre completo (nombre y apellidos del perfil)")),
			Map.entry("fechaNacimiento", fieldWithPath("fechaNacimiento")
					.description("La fecha de naciento del usuario, ")),
			Map.entry("email", fieldWithPath("email")
					.description("El correo electrónico del usuario, que será usado como su username")),
			Map.entry("password", fieldWithPath("password")
					.description("La contraseña del usuario. Debe tener más de 8 caracteres y tener letras y números")),
			Map.entry("role", fieldWithPath("role")
					.description("El rol que el perfil tiene en la biblioteca").type(JsonFieldType.STRING))
		);
	
	RequestFieldsSnippet PERFIL_REQUEST_WITHOUT_ROLE = requestFields(
			PERFIL_FIELDS.get("nombre"),
			PERFIL_FIELDS.get("fechaNacimiento"),
			PERFIL_FIELDS.get("email"),
			PERFIL_FIELDS.get("password")
		);
	
	RequestFieldsSnippet PERFIL_REQUEST = PERFIL_REQUEST_WITHOUT_ROLE.and(
			PERFIL_FIELDS.get("role")
		);
	
	ResponseFieldsSnippet PERFIL_RESPONSE = responseFields(
			PERFIL_FIELDS.get("id"),
			PERFIL_FIELDS.get("nombre"),
			PERFIL_FIELDS.get("fechaNacimiento"),
			PERFIL_FIELDS.get("email"),
			PERFIL_FIELDS.get("role")
		);
	
	PathParametersSnippet ID_PATH_PARAM = pathParameters(
			parameterWithName("id")
				.description("El identificador del perfil en la base de datos"));
	
	PathParametersSnippet ROLE_PATH_PARAM = ID_PATH_PARAM.and(
			parameterWithName("role")
				.description("El rol que el perfil tiene en la biblioteca: 'user', 'staff' o 'admin'"));
			
	
}
