package br.com.alexandre.duff.infrastructure.exception;

import br.com.alexandre.duff.infrastructure.error.ErrorCode;

public abstract class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -706767466654966363L;

	public ApplicationException() { }

	public ApplicationException(final String message) {
		super(message);
	}

	public ApplicationException(final Throwable cause) {
		super(cause);
	}

	public String getCode() {
		final boolean annotationPresent = this.getClass().isAnnotationPresent(ErrorCode.class);
		return (annotationPresent) ? this.getClass().getAnnotation(ErrorCode.class).value() : null;
	}
}
