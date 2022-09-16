package es.library.databaseserver.api.snippets;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;

public interface SecuritySnippets {

	ResponseFieldsSnippet JWT_PAIR_RESPONSE = responseFields(
			fieldWithPath("accessToken")
				.description("El token que deberá ser adjuntado en el header authorization en cada request"),
			fieldWithPath("refreshToken")
				.description("El token que se pone en /auth/token/refresh para conseguir un nuevo access token cuando este caduque")
		);
	
	RequestFieldsSnippet LOGIN_CREDENTIALS_REQUEST = requestFields(
			fieldWithPath("username")
				.description("El email con el que el usuario se registró"),
			fieldWithPath("password")
				.description("La contraseña con la que el usuario se registró")
		);
	
	PathParametersSnippet USERNAME_PATH_PARAM = pathParameters(
			parameterWithName("username")
				.description("El email con el que el usuario se registró"));
	
	PathParametersSnippet TOKEN_PATH_PARAM = pathParameters(
			parameterWithName("token")
			.description("El refresh token para conseguir un nuevo access token cuando este caduque"));
}
