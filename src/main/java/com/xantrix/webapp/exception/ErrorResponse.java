package com.xantrix.webapp.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = -7886722584628261082L;
	
	@JsonProperty(value = "date")
	private Date date;
	
	@JsonProperty(value = "code")
	private int codiceDiStato;
	
	@JsonProperty(value = "messages")
	private List<String> messaggi;
	
	public ErrorResponse() {
		this(HttpStatus.SERVICE_UNAVAILABLE.value(), null, new Date());
	}
	
	public ErrorResponse(int codiceDiStato, List<String> messaggi, Date date) {
		super();
		this.codiceDiStato = codiceDiStato;
		this.messaggi = messaggi;
		this.date = date;
	}

	public int getCodiceDiStato() {
		return codiceDiStato;
	}

	public void setCodiceDiStato(int codiceDiStato) {
		this.codiceDiStato = codiceDiStato;
	}

	public List<String> getMessaggi() {
		return messaggi;
	}

	public void setMessaggi(List<String> messaggi) {
		this.messaggi = messaggi;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorResponse [codiceDiStato=");
		builder.append(codiceDiStato);
		builder.append(", messaggi=");
		builder.append(messaggi);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
}
