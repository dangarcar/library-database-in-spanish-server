package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import java.util.List;
import java.util.Map;

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
				.description("Las palabras claves con las que se buscará el contenidos, separados por espacios"),
			parameterWithName("titulo")
				.description("El título del contenido"),
			parameterWithName("autor")
				.description("El autor del contenido"),
			parameterWithName("minAno")
				.description("El año en el que se creó el contenido (mínimo)"),
			parameterWithName("maxAno")
				.description("El año en el que se creó el contenido (máximo)"),
			parameterWithName("idioma")
				.description("El idioma en el que está hecho el contenido"),
			parameterWithName("soporte")
				.description("El soporte en el que está el contenido"),
			parameterWithName("minPaginas")
				.description("El número de páginas del libro (mínimo)"),
			parameterWithName("maxPaginas")
				.description("El número de páginas del libro (máximo)"),
			parameterWithName("editorial")
				.description("La editorial del libro"),
			parameterWithName("isbn")
				.description("El ISBN del libro"),
			parameterWithName("minEdad")
				.description("La edad mínima recomendada para ver el contenido (mínimo)"),
			parameterWithName("maxEdad")
				.description("La edad mínima recomendada para ver el contenido (máximo)"),
			parameterWithName("minDuracion")
				.description("La duración del contenido audiovisual en minutos (mínimo)"),
			parameterWithName("maxDuracion")
				.description("La duración del contenido audiovisual en minutos (máximo)"),
			parameterWithName("minCalidad")
				.description("El número de píxeles que tiene el vídeo (mínimo)"),
			parameterWithName("maxCalidad")
				.description("El número de píxeles que tiene el vídeo (máximo)"),
			parameterWithName("type")
				.description("El tipo de contenido que puede ser: audio, video o libro"),
			parameterWithName("d")
				.description("Si el contenido está actualmente disponible o no"),
			parameterWithName("p")
				.description("Indica si el contenido es prestable o no")
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
			.description("Las palabras claves con las que se buscará el contenidos, separados por espacios"),
		parameterWithName("titulo")
			.description("El título del contenido"),
		parameterWithName("autor")
			.description("El autor del contenido"),
		parameterWithName("minAno")
			.description("El año en el que se creó el contenido (mínimo)"),
		parameterWithName("maxAno")
			.description("El año en el que se creó el contenido (máximo)"),
		parameterWithName("idioma")
			.description("El idioma en el que está hecho el contenido"),
		parameterWithName("soporte")
			.description("El soporte en el que está el contenido"),
		parameterWithName("minPaginas")
			.description("El número de páginas del libro (mínimo)"),
		parameterWithName("maxPaginas")
			.description("El número de páginas del libro (máximo)"),
		parameterWithName("editorial")
			.description("La editorial del libro"),
		parameterWithName("isbn")
			.description("El ISBN del libro"),
		parameterWithName("minEdad")
			.description("La edad mínima recomendada para ver el contenido (mínimo)"),
		parameterWithName("maxEdad")
			.description("La edad mínima recomendada para ver el contenido (máximo)"),
		parameterWithName("minDuracion")
			.description("La duración del contenido audiovisual en minutos (mínimo)"),
		parameterWithName("maxDuracion")
			.description("La duración del contenido audiovisual en minutos (máximo)"),
		parameterWithName("minCalidad")
			.description("El número de píxeles que tiene el vídeo (mínimo)"),
		parameterWithName("maxCalidad")
			.description("El número de píxeles que tiene el vídeo (máximo)"),
		parameterWithName("type")
			.description("El tipo de contenido que puede ser: audio, video o libro")
	);
	
	RequestParametersSnippet TOP_PRESTAMOS_PARAMS = requestParameters(
			parameterWithName("limit").description("El número de contenidos que serán devueltos"));
	
}
