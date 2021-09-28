package br.com.mrms.meetings.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

	private String field;

	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}
	
	
}
