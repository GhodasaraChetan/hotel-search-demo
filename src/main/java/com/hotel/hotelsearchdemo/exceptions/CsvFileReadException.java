package com.hotel.hotelsearchdemo.exceptions;

import lombok.Getter;

/**
 * Exception for any CSV data loading error.
 */
@Getter
public class CsvFileReadException extends RuntimeException {
	private final String message;
	private final String errorCode;

	public CsvFileReadException(String errorCode, String message) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}
}
