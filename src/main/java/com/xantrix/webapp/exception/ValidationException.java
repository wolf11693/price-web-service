package com.xantrix.webapp.exception;

import java.io.Serializable;

public class ValidationException extends Exception implements Serializable{

	private static final long serialVersionUID = 3911950931565388844L;
	
	private String message = "Elemento Ricercato Non Trovato!";
	
	public ValidationException(String message) {
		super(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotFoundException [message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
