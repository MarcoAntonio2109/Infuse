package br.com.infuse.crudsb.dto;

import org.springframework.http.HttpStatus;

public class RetornoDTO {

	private HttpStatus status;
	int statusCode;
	Object response;

	public RetornoDTO(HttpStatus httpStatus, Object objeto) {

		this.status = httpStatus;
	    this.statusCode = httpStatus.value();
	    this.response = objeto;
	 
	    if (objeto==null || objeto.toString().equals("[]")) {
	    	this.status = HttpStatus.NOT_FOUND;
		    this.statusCode = HttpStatus.NOT_FOUND.value();
		    this.response = "Nenhum registro encontrato.";
		}    
	}

	public RetornoDTO(HttpStatus httpStatus) {
		this.status = httpStatus;
	    this.statusCode = httpStatus.value();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}