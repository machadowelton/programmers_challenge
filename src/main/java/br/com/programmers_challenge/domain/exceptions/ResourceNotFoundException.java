package br.com.programmers_challenge.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8832231011697617161L;
	
	public ResourceNotFoundException(String msg) { super(msg); }
	
	public ResourceNotFoundException(Throwable t) { super(t); }
	
	public ResourceNotFoundException(String msg, Throwable t) { super(msg, t); }

}
