package es.library.databaseserver.shared.exceptions;

import java.time.ZonedDateTime;

public class ApiError {

	private final int statusCode;
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

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}
	
}
