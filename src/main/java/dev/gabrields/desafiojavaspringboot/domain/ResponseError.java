package dev.gabrields.desafiojavaspringboot.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseError implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5549249661028087853L;
	@JsonProperty("status_code")
	private int statusCode;
	private String message;
	
	public ResponseError(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
