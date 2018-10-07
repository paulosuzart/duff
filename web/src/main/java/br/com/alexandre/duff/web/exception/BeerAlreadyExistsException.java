package br.com.alexandre.duff.web.exception;

import br.com.alexandre.duff.infrastructure.error.ErrorCode;
import br.com.alexandre.duff.infrastructure.exception.ApplicationException;

@ErrorCode("DUFF.008")
public class BeerAlreadyExistsException extends ApplicationException {

	private static final long serialVersionUID = 1407408821073671808L;

	public BeerAlreadyExistsException(final Throwable cause) {
		super(cause);
	}	
}
