package br.com.alexandre.duff.web.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class BeerServiceTest {

	@Autowired
	private BeerService beerService;

	@Test
	public void shouldCalculateAverage() {
		assertEquals(5, beerService.calculateAverage(1, 10));
		assertEquals(3, beerService.calculateAverage(1, 5));
		assertEquals(2, beerService.calculateAverage(-4, 8));
		assertEquals(2, beerService.calculateAverage(-2, 6));
		assertEquals(-3, beerService.calculateAverage(-8, 2));
		assertEquals(1, beerService.calculateAverage(-4, 6));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowAnExceptionIfMinIsGreaterThanMax() {
		beerService.calculateAverage(10, 2);
	}

}


