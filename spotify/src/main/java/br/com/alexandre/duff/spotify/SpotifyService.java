package br.com.alexandre.duff.spotify;

import br.com.alexandre.duff.spotify.domain.PlaylistResponse;
import br.com.alexandre.duff.spotify.domain.TracksResponse;
import br.com.alexandre.duff.spotify.exception.SpotifyConfigurationException;
import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SpotifyService {

  private final String clientId;
  private final String clientSecret;
  private final WebClient.Builder builder;

  private WebClient webClient;

  private String baseUrl = "https://api.spotify.com/v1";
  private String authorizationUrl = "https://accounts.spotify.com/api/token";
  private int playlistLimit = 2;
  private int tracksLimit = 10;

  @PostConstruct
  private void start() {
    this.webClient = builder.baseUrl(baseUrl).build();
  }

  private Logger logger = LoggerFactory.getLogger(SpotifyService.class);


  public Mono<PlaylistResponse> searchPlayLists(final String q, final String authorization) {

    return webClient.get()
      .uri(builder -> builder.path("/search")
        .queryParam("q", q)
        .queryParam("type", "playlist")
        .queryParam("limit", playlistLimit)
        .build()
      ).header("Authorization", String.format("Bearer %s", authorization))
      .retrieve()
      .bodyToMono(PlaylistResponse.class);
  }

  public Mono<TracksResponse> getTracksFromPlaylist(final String href, final String authorization) {

    UriComponents hrefComponents = UriComponentsBuilder.fromHttpUrl(href)
      .queryParam("limit", tracksLimit)
      .encode().build();

    return webClient.get().uri(hrefComponents.toUri())
      .header("Authorization", String.format("Bearer %s", authorization))
      .retrieve()
      .bodyToMono(TracksResponse.class);
  }

  public Mono<String> getAuthorizationToken() {
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

    return webClient.post().uri(URI.create(authorizationUrl))
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(BodyInserters.fromMultipartData(form))
      .header("Authorization", authorization)
      .retrieve()
      .bodyToMono(String.class);

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

//	protected <T> T tryToExecuteRequest(final String url, final HttpMethod method, final HttpEntity<?> entity, final Class<T> klass) {
//		try {
//			return executeRequest(url, method, entity, klass);
//		} catch (final InternalServerErrorException e) {
//			throw new SpotifyErrorException(e);
//		} catch (ServiceUnavailableException | NotFoundException e) {
//			throw new SpotifyUnavailableException(e);
//		}
//	}

}
