package com.hotel.hotelsearchdemo.exceptions;

import lombok.Getter;

/**
 * Exception for RateLimit validation failure and api suspension.
 */
@Getter
public class ApiSuspendedException extends RuntimeException {
	private final String message;
	private final String errorCode;

	public ApiSuspendedException(String errorCode, String message) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}
}
