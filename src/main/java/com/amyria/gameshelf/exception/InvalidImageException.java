package com.amyria.gameshelf.exception;

public class InvalidImageException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidImageException(String message) {
		super(message);
	}
	
}
