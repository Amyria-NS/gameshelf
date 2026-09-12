package com.amyria.gameshelf.exception;

public class FileNotImageException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5661727460322677886L;
	
	public FileNotImageException () {
		super();
	}
	
	public FileNotImageException(String message) {
		super(message);
	}
	
	public FileNotImageException(String message, Throwable err) {
		super(message,err);
	}


}
