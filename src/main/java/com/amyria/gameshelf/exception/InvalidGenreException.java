package com.amyria.gameshelf.exception;

public class InvalidGenreException extends RuntimeException{
	
	public InvalidGenreException() {
		super();
	}
	
	public InvalidGenreException(String message) {
		super(message);
	}
	
	public InvalidGenreException(String message, Throwable err) {
		super(message, err);
	}

}
