package es.library.databaseserver.api;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public interface ApiTestUtils {

	OperationRequestPreprocessor HTTP_REQUEST = preprocessRequest(removeHeaders("Host","Content-Length"), prettyPrint());
	
	OperationResponsePreprocessor HTTP_RESPONSE = preprocessResponse(removeHeaders("Vary","Content-Length"), prettyPrint());
	
}
