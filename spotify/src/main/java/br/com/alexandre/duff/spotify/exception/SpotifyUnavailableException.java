package br.com.alexandre.duff.spotify.exception;

import br.com.alexandre.duff.infrastructure.error.ErrorCode;
import br.com.alexandre.duff.infrastructure.exception.ApplicationException;

@ErrorCode("DUFF.005")
public class SpotifyUnavailableException extends ApplicationException {

	private static final long serialVersionUID = -5215032377154679945L;

	public SpotifyUnavailableException(final Throwable cause) {
		super(cause);
	}
	
}
