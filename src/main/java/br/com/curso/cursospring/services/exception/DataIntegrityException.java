package br.com.curso.cursospring.services.exception;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String exception) {
		super(exception);
	}
	
	public DataIntegrityException(String exception, Throwable cause) {
		super(exception,cause);
	}

}
