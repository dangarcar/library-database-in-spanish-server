package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import es.library.databaseserver.contenido.crud.controller.ContenidoController;
import es.library.databaseserver.shared.exceptions.GlobalExceptionHandler;

//Está terminada

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class ErrorApiTest {
	
	@Autowired
	private ContenidoController controller;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
			RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new GlobalExceptionHandler())
				.apply(documentationConfiguration(restDocumentation))
				.build();
	}
	
	@Test
	void errorTest() throws Exception {
		
		mockMvc.perform(delete("/contenidos/{id}",-6872387682637L)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is5xxServerError())
			.andDo(document("error", 
					responseFields(
							fieldWithPath("statusCode")
								.description("El código HTTP del error"),
							fieldWithPath("timestamp")
								.description("El momento en el que el error se ha comunicado, codificado en ISO-8601"),
							fieldWithPath("message")
								.description("El mensaje de error"))));
	}
	
}
