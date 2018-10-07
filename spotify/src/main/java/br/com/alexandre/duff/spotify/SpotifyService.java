package br.com.alexandre.duff.spotify;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alexandre.duff.infrastructure.RestClient;
import br.com.alexandre.duff.infrastructure.RestClient.Exceptions.InternalServerErrorException;
import br.com.alexandre.duff.infrastructure.RestClient.Exceptions.NotFoundException;
import br.com.alexandre.duff.infrastructure.RestClient.Exceptions.ServiceUnavailableException;
import br.com.alexandre.duff.spotify.domain.PlaylistResponse;
import br.com.alexandre.duff.spotify.domain.TracksResponse;
import br.com.alexandre.duff.spotify.exception.SpotifyConfigurationException;
import br.com.alexandre.duff.spotify.exception.SpotifyErrorException;
import br.com.alexandre.duff.spotify.exception.SpotifyUnavailableException;

public class SpotifyService extends RestClient {

	private String clientId;
	private String clientSecret;
	
	private String baseUrl = "https://api.spotify.com/v1";	
	private String authorizationUrl = "https://accounts.spotify.com/api/token";
	private int playlistLimit = 2;
	private int tracksLimit = 10;
	
	private Logger logger = LoggerFactory.getLogger(SpotifyService.class);
		
	public SpotifyService(final String clientId, final String clientSecret, final RestTemplate restTemplate) {
		super(restTemplate);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	public PlaylistResponse searchPlaylists(final String q, final String authorization) {
		if (baseUrl == null) {
			throw new SpotifyConfigurationException("baseUrl is null");
		}
		final String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search")
			.queryParam("q", q)
			.queryParam("type", "playlist")
			.queryParam("limit", playlistLimit)
			.encode().build().toString();
	
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", authorization));
		
		return tryToExecuteRequest(url, HttpMethod.GET, new HttpEntity<>(headers), PlaylistResponse.class);
	}
	
	public TracksResponse getTracksFromPlaylist(final String href, final String authorization) {
		if (href == null) {
			throw new SpotifyConfigurationException("href is null");
		}
		final String url = UriComponentsBuilder.fromHttpUrl(href)
			.queryParam("limit", tracksLimit)
			.encode().build().toString();		
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", authorization));
		
		return tryToExecuteRequest(url, HttpMethod.GET, new HttpEntity<>(headers), TracksResponse.class);
	}
	
	public String getAuthorizationToken() {
		if (clientId == null) {
			throw new SpotifyConfigurationException("client_id is null");
		}
		if (clientSecret == null) {
			throw new SpotifyConfigurationException("client_secret is null");			
		}
		if (authorizationUrl == null) {
			throw new SpotifyConfigurationException("authorizationUrl is null");
		}
		final String authorization = String.format("Basic %s", Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
		logger.debug("Authorization Generated: '{}'", authorization);		
		
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authorization);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		final MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("grant_type", "client_credentials");

		return tryToExecuteRequest(authorizationUrl, HttpMethod.POST, 
				new HttpEntity<MultiValueMap<String, String>>(form, headers), Map.class)
				.get("access_token").toString();
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setAuthorizationUrl(String authorizationUrl) {
		this.authorizationUrl = authorizationUrl;
	}
	
	public void setPlaylistLimit(int playlistLimit) {
		this.playlistLimit = playlistLimit;
	}

	public void setTracksLimit(int tracksLimit) {
		this.tracksLimit = tracksLimit;
	}

	protected <T> T tryToExecuteRequest(final String url, final HttpMethod method, final HttpEntity<?> entity, final Class<T> klass) {
		try {
			return executeRequest(url, method, entity, klass);
		} catch (final InternalServerErrorException e) {
			throw new SpotifyErrorException(e);
		} catch (ServiceUnavailableException | NotFoundException e) {
			throw new SpotifyUnavailableException(e);
		}		
	}

}
