package br.com.alexandre.duff.spotify.exception;

import br.com.alexandre.duff.infrastructure.error.ErrorCode;
import br.com.alexandre.duff.infrastructure.exception.ApplicationException;

@ErrorCode("DUFF.007")
public class SpotifyAuthorizationError extends ApplicationException {

	private static final long serialVersionUID = 1428773700820098402L;

	public SpotifyAuthorizationError(final String message) {
		super(message);
	}
	
}