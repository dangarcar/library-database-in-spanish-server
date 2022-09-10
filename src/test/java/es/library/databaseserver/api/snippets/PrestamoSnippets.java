package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import java.util.Map;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;

public interface PrestamoSnippets {

	Map<String, FieldDescriptor> PRESTAMO_FIELDS = Map.ofEntries(
			Map.entry("id", fieldWithPath("id")
					.description("El identificador del préstamo en la base de datos")),
			Map.entry("idperfil", fieldWithPath("idperfil")
					.description("El identificador en la base de datos del perfil que coge prestado al contenido")),
			Map.entry("idcontenido", fieldWithPath("idcontenido")
					.description("El identificador en la base de datos del contenido que es cogido en préstamo")),
			Map.entry("fechaHoraPrestamo", fieldWithPath("fechaHoraPrestamo")
					.description("La fecha y hora en la que se produjo el préstamo")),
			Map.entry("fechaHoraDevolucion", fieldWithPath("fechaHoraDevolucion")
					.description("La fecha y hora en la que se produjo la devolución").type(JsonFieldType.STRING)),
			Map.entry("diasdePrestamo", fieldWithPath("diasdePrestamo")
					.description("El número de días por los que el contenido puede ser prestado como máximo")),
			Map.entry("devuelto", fieldWithPath("devuelto")
					.description("Si el contenido ha sido devuelto o no"))
		);
	
	RequestFieldsSnippet PRESTAMO_REQUEST = requestFields(
			PRESTAMO_FIELDS.get("idperfil"),
			PRESTAMO_FIELDS.get("idcontenido"),
			PRESTAMO_FIELDS.get("fechaHoraPrestamo"),
			PRESTAMO_FIELDS.get("fechaHoraDevolucion").optional(),
			PRESTAMO_FIELDS.get("diasdePrestamo"),
			PRESTAMO_FIELDS.get("devuelto"));
	
	ResponseFieldsSnippet PRESTAMO_RESPONSE = responseFields(
			PRESTAMO_FIELDS.get("id"),
			PRESTAMO_FIELDS.get("idperfil"),
			PRESTAMO_FIELDS.get("idcontenido"),
			PRESTAMO_FIELDS.get("fechaHoraPrestamo"),
			PRESTAMO_FIELDS.get("fechaHoraDevolucion").optional(),
			PRESTAMO_FIELDS.get("diasdePrestamo"),
			PRESTAMO_FIELDS.get("devuelto"));
	
	PathParametersSnippet ID_PATH_PARAM = pathParameters(
			parameterWithName("id")
				.description("El identificador del préstamo en la base de datos"));
	
	RequestParametersSnippet PRESTAR_DEVOLVER_PARAMS = requestParameters(
			parameterWithName("perfil")
				.description("El identificador en la base de datos del perfil que coge prestado al contenido"),
			parameterWithName("contenido")
				.description("El identificador en la base de datos del contenido que es cogido en préstamo")
		);
	
}
