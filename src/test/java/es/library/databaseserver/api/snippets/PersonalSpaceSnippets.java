package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;

public interface PersonalSpaceSnippets {

	PathParametersSnippet USERNAME_PATH_PARAM = pathParameters(
			parameterWithName("username")
				.description("El correo electrónico del usuario"));
	
	ResponseFieldsSnippet LIST_CONTENIDO = responseFields(
			fieldWithPath("[]")
				.description("La lista de audios, vídeos y libros"));
	
	ResponseFieldsSnippet LIST_PRESTAMOS = responseFields(
			fieldWithPath("[]")
				.description("La lista de préstamos"));
	
}
