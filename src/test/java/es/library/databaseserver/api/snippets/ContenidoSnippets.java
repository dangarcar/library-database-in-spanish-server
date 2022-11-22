package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import java.util.Map;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;

public interface ContenidoSnippets {

	Map<String, FieldDescriptor> CONTENIDO_FIELDS = Map.ofEntries(
			Map.entry("type", fieldWithPath("type")
				.description("El tipo de contenido que puede ser: audio, video o libro")),
			Map.entry("id", fieldWithPath("id")
					.description("El identificador del contenido en la base de datos")),
			Map.entry("titulo", fieldWithPath("titulo")
				.description("El título del contenido")), 
			Map.entry("autor", fieldWithPath("autor")
				.description("El autor del contenido")),
			Map.entry("descripcion", fieldWithPath("descripcion")
				.description("La descripción del contenido")), 
			Map.entry("ano", fieldWithPath("ano")
				.description("El año en el que se creó el contenido")),
			Map.entry("idioma", fieldWithPath("idioma")
				.description("El idioma en el que está hecho el contenido")), 
			Map.entry("soporte", fieldWithPath("soporte")
				.description("El soporte en el que está el contenido")),
			Map.entry("prestable", fieldWithPath("prestable")
				.description("Indica si el contenido es prestable o no")), 
			Map.entry("diasDePrestamo", fieldWithPath("diasDePrestamo")
				.description("Los días que el contenido será prestado")),
			Map.entry("disponible", fieldWithPath("disponible")
				.description("Si el contenido está actualmente disponible o no")),
			Map.entry("imagen", fieldWithPath("imagen")
					.description("La URL de la imagen del contenido. Puede ser nula")),
			
			Map.entry("duracion", fieldWithPath("duracion")
				.description("La duración del contenido audiovisual en minutos")),
			
			Map.entry("edadRecomendada", fieldWithPath("edadRecomendada")
				.description("La edad mínima recomendada para ver el contenido")),	
			Map.entry("calidad", fieldWithPath("calidad")
				.description("El número de píxeles que tiene el vídeo")),
			
			Map.entry("isbn", fieldWithPath("isbn")
				.description("El ISBN del libro")),
			Map.entry("paginas", fieldWithPath("paginas")
				.description("El número de páginas del libro")), 
			Map.entry("editorial", fieldWithPath("editorial")
				.description("La editorial del libro"))
		);
	
	RequestFieldsSnippet CONTENIDO_REQUEST = requestFields(
			CONTENIDO_FIELDS.get("type"),
			CONTENIDO_FIELDS.get("titulo"),
			CONTENIDO_FIELDS.get("autor"),
			CONTENIDO_FIELDS.get("descripcion"),
			CONTENIDO_FIELDS.get("ano"),
			CONTENIDO_FIELDS.get("idioma"),
			CONTENIDO_FIELDS.get("soporte"),
			CONTENIDO_FIELDS.get("prestable"),
			CONTENIDO_FIELDS.get("diasDePrestamo"),
			CONTENIDO_FIELDS.get("disponible"),
			CONTENIDO_FIELDS.get("imagen")
		);

	RequestFieldsSnippet AUDIO_REQUEST = CONTENIDO_REQUEST.and(
			CONTENIDO_FIELDS.get("duracion")
		);

	RequestFieldsSnippet VIDEO_REQUEST = AUDIO_REQUEST.and(
			CONTENIDO_FIELDS.get("edadRecomendada"),
			CONTENIDO_FIELDS.get("calidad")
		);

	RequestFieldsSnippet LIBRO_REQUEST = CONTENIDO_REQUEST.and(
			CONTENIDO_FIELDS.get("isbn"),
			CONTENIDO_FIELDS.get("paginas"),
			CONTENIDO_FIELDS.get("editorial")
		);
	
	ResponseFieldsSnippet CONTENIDO_RESPONSE = responseFields(
			CONTENIDO_FIELDS.get("id"),
			CONTENIDO_FIELDS.get("type"),
			CONTENIDO_FIELDS.get("titulo"),
			CONTENIDO_FIELDS.get("autor"),
			CONTENIDO_FIELDS.get("descripcion"),
			CONTENIDO_FIELDS.get("ano"),
			CONTENIDO_FIELDS.get("idioma"),
			CONTENIDO_FIELDS.get("soporte"),
			CONTENIDO_FIELDS.get("prestable"),
			CONTENIDO_FIELDS.get("diasDePrestamo"),
			CONTENIDO_FIELDS.get("disponible"),
			CONTENIDO_FIELDS.get("imagen")
		);

	ResponseFieldsSnippet AUDIO_RESPONSE = CONTENIDO_RESPONSE.and(
			CONTENIDO_FIELDS.get("duracion")
		);

	ResponseFieldsSnippet VIDEO_RESPONSE = AUDIO_RESPONSE.and(
			CONTENIDO_FIELDS.get("edadRecomendada"),
			CONTENIDO_FIELDS.get("calidad")
		);

	ResponseFieldsSnippet LIBRO_RESPONSE = CONTENIDO_RESPONSE.and(
			CONTENIDO_FIELDS.get("isbn"),
			CONTENIDO_FIELDS.get("paginas"),
			CONTENIDO_FIELDS.get("editorial")
		);
	
	PathParametersSnippet ID_PATH_PARAM = pathParameters(
			parameterWithName("id")
				.description("El identificador del contenido en la base de datos"));
	
	
	
}
