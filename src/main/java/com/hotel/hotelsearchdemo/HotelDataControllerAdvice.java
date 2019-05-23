package com.hotel.hotelsearchdemo;

import com.hotel.hotelsearchdemo.beans.ErrorResponse;
import com.hotel.hotelsearchdemo.exceptions.ApiSuspendedException;
import com.hotel.hotelsearchdemo.exceptions.CsvFileReadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller Advice for exception handling.
 */
@Slf4j
@RestControllerAdvice
public class HotelDataControllerAdvice {

	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler(ApiSuspendedException.class)
	ErrorResponse apiSuspendedException(ApiSuspendedException e) {
		log.error(e.getMessage());
		return ErrorResponse.builder()
				.errorCode(e.getErrorCode())
				.detail(e.getMessage())
				.build();
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(CsvFileReadException.class)
	ErrorResponse csvFileReadException(CsvFileReadException e) {
		log.error(e.getMessage());
		return ErrorResponse.builder()
				.errorCode(e.getErrorCode())
				.detail(e.getMessage())
				.build();
	}
}
