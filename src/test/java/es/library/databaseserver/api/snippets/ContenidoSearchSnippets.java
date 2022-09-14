package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.util.List;
import java.util.Map;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public interface ContenidoSearchSnippets {

	MultiValueMap<String, String> GET_CONTENIDOS_PARAMS_MAP = new LinkedMultiValueMap<String, String>(Map.ofEntries(
			Map.entry("q", 		List.of("dgf")),
			Map.entry("titulo", 	List.of("gf")),
			Map.entry("autor", 		List.of("s")),
			Map.entry("minAno",		List.of("12")),
			Map.entry("maxAno",		List.of("1234")),
			Map.entry("idioma",		List.of("esp")),
			Map.entry("soporte",	List.of("FISICO")),
			Map.entry("minPaginas",	List.of("34")),
			Map.entry("maxPaginas",	List.of("1324326")),
			Map.entry("editorial", 	List.of("aa")),
			Map.entry("isbn",		List.of("aa")),
			Map.entry("minEdad",	List.of("10")),
			Map.entry("maxEdad",	List.of("10")),
			Map.entry("minDuracion",List.of("10")),
			Map.entry("maxDuracion",List.of("10")),
			Map.entry("minCalidad",	List.of("10")),
			Map.entry("maxCalidad",	List.of("10")),
			Map.entry("type",		List.of("audio")),
			Map.entry("d",			List.of("true")),
			Map.entry("p", 			List.of("true"))
		));
	
	RequestParametersSnippet GET_CONTENIDOS_PARAMS_SNIPPET = requestParameters(
			parameterWithName("q")
				.description("Las palabras claves con las que se buscarán los contenidos, separados por espacios").optional(),
			parameterWithName("titulo")
				.description("El título del contenido").optional(),
			parameterWithName("autor")
				.description("El autor del contenido").optional(),
			parameterWithName("minAno")
				.description("El año en el que se creó el contenido (mínimo)").optional(),
			parameterWithName("maxAno")
				.description("El año en el que se creó el contenido (máximo)").optional(),
			parameterWithName("idioma")
				.description("El idioma en el que está hecho el contenido").optional(),
			parameterWithName("soporte")
				.description("El soporte en el que está el contenido").optional(),
			parameterWithName("minPaginas")
				.description("El número de páginas del libro (mínimo)").optional(),
			parameterWithName("maxPaginas")
				.description("El número de páginas del libro (máximo)").optional(),
			parameterWithName("editorial")
				.description("La editorial del libro").optional(),
			parameterWithName("isbn")
				.description("El ISBN del libro").optional(),
			parameterWithName("minEdad")
				.description("La edad mínima recomendada para ver el contenido (mínimo)").optional(),
			parameterWithName("maxEdad")
				.description("La edad mínima recomendada para ver el contenido (máximo)").optional(),
			parameterWithName("minDuracion")
				.description("La duración del contenido audiovisual en minutos (mínimo)").optional(),
			parameterWithName("maxDuracion")
				.description("La duración del contenido audiovisual en minutos (máximo)").optional(),
			parameterWithName("minCalidad")
				.description("El número de píxeles que tiene el vídeo (mínimo)").optional(),
			parameterWithName("maxCalidad")
				.description("El número de píxeles que tiene el vídeo (máximo)").optional(),
			parameterWithName("type")
				.description("El tipo de contenido que puede ser: audio, video o libro").optional(),
			parameterWithName("d")
				.description("Si el contenido está actualmente disponible o no").optional(),
			parameterWithName("p")
				.description("Indica si el contenido es prestable o no").optional()
		);

	MultiValueMap<String, String> GET_UNIQUE_CONTENIDOS_PARAMS_MAP = new LinkedMultiValueMap<String, String>(Map.ofEntries(
			Map.entry("q", 		List.of("dgf")),
			Map.entry("titulo", 	List.of("gf")),
			Map.entry("autor", 		List.of("s")),
			Map.entry("minAno",		List.of("12")),
			Map.entry("maxAno",		List.of("1234")),
			Map.entry("idioma",		List.of("esp")),
			Map.entry("soporte",	List.of("FISICO")),
			Map.entry("minPaginas",	List.of("34")),
			Map.entry("maxPaginas",	List.of("1324326")),
			Map.entry("editorial", 	List.of("aa")),
			Map.entry("isbn",		List.of("aa")),
			Map.entry("minEdad",	List.of("10")),
			Map.entry("maxEdad",	List.of("10")),
			Map.entry("minDuracion",List.of("10")),
			Map.entry("maxDuracion",List.of("10")),
			Map.entry("minCalidad",	List.of("10")),
			Map.entry("maxCalidad",	List.of("10")),
			Map.entry("type",		List.of("audio"))
		));

	RequestParametersSnippet GET_UNIQUE_CONTENIDOS_PARAMS_SNIPPET = requestParameters(
		parameterWithName("q")
			.description("Las palabras claves con las que se buscará el contenidos, separados por espacios").optional(),
		parameterWithName("titulo")
			.description("El título del contenido").optional(),
		parameterWithName("autor")
			.description("El autor del contenido").optional(),
		parameterWithName("minAno")
			.description("El año en el que se creó el contenido (mínimo)").optional(),
		parameterWithName("maxAno")
			.description("El año en el que se creó el contenido (máximo)").optional(),
		parameterWithName("idioma")
			.description("El idioma en el que está hecho el contenido").optional(),
		parameterWithName("soporte")
			.description("El soporte en el que está el contenido").optional(),
		parameterWithName("minPaginas")
			.description("El número de páginas del libro (mínimo)").optional(),
		parameterWithName("maxPaginas")
			.description("El número de páginas del libro (máximo)").optional(),
		parameterWithName("editorial")
			.description("La editorial del libro").optional(),
		parameterWithName("isbn")
			.description("El ISBN del libro").optional(),
		parameterWithName("minEdad")
			.description("La edad mínima recomendada para ver el contenido (mínimo)").optional(),
		parameterWithName("maxEdad")
			.description("La edad mínima recomendada para ver el contenido (máximo)").optional(),
		parameterWithName("minDuracion")
			.description("La duración del contenido audiovisual en minutos (mínimo)").optional(),
		parameterWithName("maxDuracion")
			.description("La duración del contenido audiovisual en minutos (máximo)").optional(),
		parameterWithName("minCalidad")
			.description("El número de píxeles que tiene el vídeo (mínimo)").optional(),
		parameterWithName("maxCalidad")
			.description("El número de píxeles que tiene el vídeo (máximo)").optional(),
		parameterWithName("type")
			.description("El tipo de contenido que puede ser: audio, video o libro").optional()
	);
	
	RequestParametersSnippet TOP_PRESTAMOS_PARAMS = requestParameters(
			parameterWithName("limit")
				.description("El número de contenidos que serán devueltos, por defecto 10").optional());
	
	ResponseFieldsSnippet UNIQUE_CONTENIDOS_RESPONSE = responseFields(
			subsectionWithPath("[]").description("Un array de diferentes modelos de contenidos"),
			fieldWithPath("[].titulo").optional().type(JsonFieldType.STRING)
				.description("El título del contenido"),
			fieldWithPath("[].autor").optional().type(JsonFieldType.STRING)
				.description("El autor del contenido"),
			fieldWithPath("[].descripcion").optional().type(JsonFieldType.STRING)
				.description("La descripción del contenido"),
			fieldWithPath("[].ano").optional().type(JsonFieldType.NUMBER)
				.description("El año en el que se creó el contenido"),
			fieldWithPath("[].idioma").optional().type(JsonFieldType.STRING)
				.description("El idioma en el que está hecho el contenido"),
			fieldWithPath("[].soporte").optional().type(JsonFieldType.STRING)
				.description("El soporte en el que está el contenido"),
			fieldWithPath("[].ids[]").optional().type(JsonFieldType.ARRAY)
				.description("Las ID de los contenidos que comparten las anteriores características")
		);
	
}
