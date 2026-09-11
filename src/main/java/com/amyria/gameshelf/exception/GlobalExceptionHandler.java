package com.amyria.gameshelf.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amyria.gameshelf.dto.ErrorResponse;

import tools.jackson.databind.exc.UnrecognizedPropertyException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidGenreException.class)
	public ResponseEntity<ErrorResponse> handleInvalidGenre(InvalidGenreException e){
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e){
		StringBuilder errorMessage = new StringBuilder("Validation failed. ");
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		for (FieldError err : errors) {
			errorMessage.append(err.getField()).append(" ").append(err.getDefaultMessage()).append(". ");
		}
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.toString());
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(UnrecognizedPropertyException.class)
	public ResponseEntity<ErrorResponse> handleUnrecognizedProperty(UnrecognizedPropertyException e){
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Unknown field: " + e.getPropertyName());
		return ResponseEntity.badRequest().body(error);
	}

}
