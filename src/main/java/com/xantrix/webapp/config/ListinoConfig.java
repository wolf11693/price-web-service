package com.xantrix.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("listino")	// prefix configuration in properties file
public class ListinoConfig {
	private String id;

	public ListinoConfig() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListinoConfig [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
