package br.com.alexandre.duff.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.alexandre.duff.infrastructure.RestClient.Exceptions.InternalServerErrorException;
import br.com.alexandre.duff.infrastructure.RestClient.Exceptions.NotFoundException;
import br.com.alexandre.duff.infrastructure.RestClient.Exceptions.ServiceUnavailableException;

public abstract class RestClient {

	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(RestClient.class);

	protected RestClient(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	protected <T> T executeRequest(final String url, final HttpMethod method, final HttpEntity<?> entity, final Class<T> klass) 
			throws InternalServerErrorException, ServiceUnavailableException, NotFoundException {
		try {
			logger.debug("Invoking: '{}' with '{}' and method: '{}'", url, entity, method);
			final ResponseEntity<T> responseEntity = this.restTemplate.exchange(url,  method, entity, klass);
			return responseEntity.getBody();
		} catch (final HttpClientErrorException e) {
			logger.debug("HTTP Status: '{}'", e.getStatusText());
			switch (e.getStatusCode()) {
			case NOT_FOUND: {
				throw new Exceptions.NotFoundException();
			}
			case SERVICE_UNAVAILABLE: {
				throw new Exceptions.ServiceUnavailableException();
			}
			default: {
				throw new Exceptions.InternalServerErrorException(e.getMessage() + ": " + e.getResponseBodyAsString(), e);
			}
			}		
		} catch (final HttpServerErrorException e) {
			throw new Exceptions.InternalServerErrorException(e.getMessage(), e);
		} catch (final ResourceAccessException e) {
			throw new Exceptions.ServiceUnavailableException();
		} catch (final RestClientException e) {
			throw new Exceptions.InternalServerErrorException(e.getMessage(), e);
		}
	}

	public static class Exceptions {
		
		public static abstract class RestClientException extends Exception {
			private static final long serialVersionUID = -6386516110190057578L;

			public RestClientException() { }

			public RestClientException(final String message, final Throwable cause) {
				super(message, cause);
			}

			public RestClientException(final Throwable cause) {
				super(cause);
			}			
		}
		
		public static class NotFoundException extends RestClientException {
			private static final long serialVersionUID = 6790717610852043567L;
		}
		
		public static class InternalServerErrorException extends RestClientException {
			private static final long serialVersionUID = -7154086424033963769L;

			public InternalServerErrorException(final String message, final Throwable cause) {
				super(message, cause);
			}
		}
		
		public static class ServiceUnavailableException extends RestClientException {
			private static final long serialVersionUID = -4740935861393894584L;
		}
	}

}
