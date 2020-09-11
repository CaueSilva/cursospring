package br.com.curso.cursospring.services.exception;

public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String exception) {
		super(exception);
	}
	
	public AuthorizationException(String exception, Throwable cause) {
		super(exception,cause);
	}

}
