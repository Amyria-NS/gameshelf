package com.amyria.gameshelf.exception;

public class InvalidGenreException extends RuntimeException{
	

	/**
	 * Auto-generated serial version UID
	 */
	private static final long serialVersionUID = -8444147020408138067L;

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
