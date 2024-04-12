package br.com.infuse.crudsb.exception;

public class ProdutoException extends Exception {

	private static final long serialVersionUID = 6212826653995678795L;
	private String message;
	
	public ProdutoException(Throwable e) {
		super(e);
	}
	public ProdutoException (String message) 
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
