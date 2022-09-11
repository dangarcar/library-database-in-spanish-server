package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import java.util.List;
import java.util.Map;

import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public interface PrestamoSearchSnippets {

	MultiValueMap<String, String> GET_PRESTAMOS_PARAMS_MAP = new LinkedMultiValueMap<>(Map.ofEntries(
			Map.entry("contenido",		List.of("2453435")),
			Map.entry("perfil",			List.of("4343234246")),
			Map.entry("minDias",		List.of("10")),
			Map.entry("maxDias",		List.of("12")),
			Map.entry("fromPrestamo",	List.of("2022-07-11T07:36:13")),
			Map.entry("toPrestamo",		List.of("2022-09-11T07:36:10")),
			Map.entry("fromDevolucion",	List.of("2022-07-11T07:36:13")),
			Map.entry("toDevolucion",	List.of("2022-09-11T07:36:10")),
//			Map.entry("fromPrestamo",	List.of("2022-07-11T07:36:13+00:00")),
//			Map.entry("toPrestamo",		List.of("2022-09-11T07:36:10+00:00")),
//			Map.entry("fromDevolucion",	List.of("2022-07-11T07:36:13+00:00")),
//			Map.entry("toDevolucion",	List.of("2022-09-11T07:36:10+00:00")),
			Map.entry("d",				List.of("true"))
		));
	
	RequestParametersSnippet GET_PRESTAMOS_PARAMS_SNIPPET = requestParameters(
			parameterWithName("contenido")
				.description("El identificador en la base de datos del contenido que es cogido en préstamo"),
			parameterWithName("perfil")
				.description("El identificador en la base de datos del perfil que coge prestado al contenido"),
			parameterWithName("minDias")
				.description("El número de días por los que el contenido puede ser prestado como máximo (mínimo)"),
			parameterWithName("maxDias")
				.description("El número de días por los que el contenido puede ser prestado como máximo (máximo)"),
			parameterWithName("fromPrestamo")
				.description("La fecha y hora en la que se produjo el préstamo (mínimo)"),
			parameterWithName("toPrestamo")
				.description("La fecha y hora en la que se produjo el préstamo (máximo)"),
			parameterWithName("fromDevolucion")
				.description("La fecha y hora en la que se produjo la devolución (mínimo)"),
			parameterWithName("toDevolucion")
				.description("La fecha y hora en la que se produjo la devolución (máximo)"),
			parameterWithName("d")
				.description("Si el contenido ha sido devuelto o no")
		);
	
	
}
