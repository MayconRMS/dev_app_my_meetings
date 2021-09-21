package br.com.mrms.meetings.exception;

public class MeetingStatusException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MeetingStatusException() {
		super();
	}

	public MeetingStatusException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MeetingStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	public MeetingStatusException(String message) {
		super(message);
	}

	public MeetingStatusException(Throwable cause) {
		super(cause);
	}

	
	
}
