package com.gokul.error;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class HttpResponse {
	private String message;
	private String reason;
	private int httpstatuscode;
	private HttpStatus httpStatus;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd/MM/yyyy hh:mm:ss")
	private Date date;
	public HttpResponse(int httpstatuscode, HttpStatus httpStatus, String message, String reason) {
		super();
		this.date=new Date();
		this.httpstatuscode = httpstatuscode;
		this.httpStatus = httpStatus;
		this.message = message;
		this.reason = reason;
	}
	
	public int getHttpstatuscode() {
		return httpstatuscode;
	}
	public void setHttpstatuscode(int httpstatuscode) {
		this.httpstatuscode = httpstatuscode;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
