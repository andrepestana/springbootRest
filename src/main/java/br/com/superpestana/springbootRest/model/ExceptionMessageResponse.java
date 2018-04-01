package br.com.superpestana.springbootRest.model;

import java.util.Date;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ExceptionMessageResponse {

	private String message;
	private Status status;
	@JsonSerialize(as = Date.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd hh:mm:ss")
	private Date timestamp;
	public ExceptionMessageResponse(String message, Status status) {
		super();
		this.message = message;
		this.status = status;
		timestamp = new Date();
	}
	public String getMessage() {
		return message;
	}
	public int getStatus() {
		return status.getStatusCode();
	}
	
	public String getError() {
		return status.toString();
	}
}
