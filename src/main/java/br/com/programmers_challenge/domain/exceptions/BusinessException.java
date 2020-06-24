package br.com.programmers_challenge.domain.exceptions;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6269332221369143308L;
	
	
	public BusinessException(String msg) { super(msg); }
	
	public BusinessException(Throwable t) { super(t); }
	
	public BusinessException(String msg, Throwable t) { super(msg, t); }

}

