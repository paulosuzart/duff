package br.com.alexandre.duff.web.configuration;

import br.com.alexandre.duff.spotify.SpotifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestConfiguration {


	@Bean
	public SpotifyService spotifyService(@Value("${spotify.clientId}") final String clientId, 
			@Value("${spotify.clientSecret}") final String clientSecret, final WebClient.Builder webClient) {
		return new SpotifyService(clientId, clientSecret, webClient);
	}
	
}
