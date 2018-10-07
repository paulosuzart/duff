package br.com.alexandre.duff.web.configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.alexandre.duff.spotify.SpotifyService;

@Configuration
public class RestConfiguration {

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		final MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonMessageConverter.setObjectMapper(objectMapper);

		return jsonMessageConverter;
	}
	
	@Bean
	public RestTemplate restTemplate(final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {		
		final List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(mappingJackson2HttpMessageConverter);
		converters.add(new StringHttpMessageConverter());
		converters.add(new FormHttpMessageConverter());

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

	@Bean
	public SpotifyService spotifyService(@Value("${spotify.clientId}") final String clientId, 
			@Value("${spotify.clientSecret}") final String clientSecret, final RestTemplate restTemplate) {
		return new SpotifyService(clientId, clientSecret, restTemplate);
	}
	
}
