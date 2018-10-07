package br.com.alexandre.duff.web.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import br.com.alexandre.duff.web.repository.BeerRepository;

@Configuration
@TestPropertySource(locations = { "classpath:test.properties" })
public class TestConfiguration {

	@Bean
	public BeerService beerService() {
		return new BeerService();
	}
	
	@Bean
	public BeerRepository beerRepository() {
		return Mockito.mock(BeerRepository.class);
	}
}
