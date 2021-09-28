package br.com.mrms.meetings.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.mrms.meetings.controller.response.ErrorResponse;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse entityNotFoundHandler(EntityNotFoundException ex) {
		return new ErrorResponse("Resource not found");
	}

	@ExceptionHandler(MeetingStatusException.class)
	public ResponseEntity<?> entityNotFoundHandlerStatus(MeetingStatusException ex) {
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method not allowed")
						.withDetail("You cannot perform this operation: " + ex.getMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ErrorResponse> erros = ex.getBindingResult()
				.getFieldErrors().stream()
				.map(x -> new ErrorResponse(x.getField(), x.getDefaultMessage()))
				.collect(Collectors.toList());

		return ResponseEntity.badRequest().body(erros);
	}

}
