package core.jdbc.refactoring.exception;

public class MSQLException extends RuntimeException{
	public MSQLException() {
	}

	public MSQLException(String message) {
		super(message);
	}

	public MSQLException(String message, Throwable cause) {
		super(message, cause);
	}

	public MSQLException(Throwable cause) {
		super(cause);
	}

	public MSQLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
