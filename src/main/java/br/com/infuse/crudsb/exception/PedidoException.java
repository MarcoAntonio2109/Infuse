package br.com.infuse.crudsb.exception;

public class PedidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6212826653995678795L;
	private String message;
	
	public PedidoException(Throwable e) {
		super(e);
	}
	public PedidoException (String message) 
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
