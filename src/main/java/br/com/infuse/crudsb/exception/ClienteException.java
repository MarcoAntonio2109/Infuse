package br.com.infuse.crudsb.exception;

public class ClienteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6212826653995678795L;
	private String message;
	
	public ClienteException(Throwable e) {
		super(e);
	}
	public ClienteException (String message) 
	{
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
