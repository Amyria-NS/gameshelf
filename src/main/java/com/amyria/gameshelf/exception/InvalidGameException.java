package com.amyria.gameshelf.exception;

public class InvalidGameException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidGameException(String message) {
		super(message);
	}


}
