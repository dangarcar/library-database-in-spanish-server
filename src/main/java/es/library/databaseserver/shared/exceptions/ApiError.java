package es.library.databaseserver.shared.exceptions;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

public class ApiError {

	private final int statusCode;
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private final ZonedDateTime timestamp;
	private final String message;
	
	public ApiError(int statusCode, ZonedDateTime timestamp, String message) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}
	
	@JsonIgnore
	public String getJsonString() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		String errorMessage = mapper.writeValueAsString(this);
		return errorMessage;
	}
	
}
