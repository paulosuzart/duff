package br.com.alexandre.duff.spotify.exception;

import br.com.alexandre.duff.infrastructure.error.ErrorCode;
import br.com.alexandre.duff.infrastructure.exception.ApplicationException;

@ErrorCode("DUFF.004")
public class SpotifyErrorException extends ApplicationException {

	private static final long serialVersionUID = 613367762194289674L;

	public SpotifyErrorException(final Throwable cause) {
		super(cause);
	}	
}
