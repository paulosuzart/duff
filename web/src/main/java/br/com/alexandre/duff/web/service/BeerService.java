package br.com.alexandre.duff.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkArgument;
import br.com.alexandre.duff.domain.Beer;
import br.com.alexandre.duff.web.exception.BeerAlreadyExistsException;
import br.com.alexandre.duff.web.repository.BeerRepository;

@Service
public class BeerService {

	@Autowired
	private BeerRepository beerRepository;

	public void save(final Beer beer) {
		checkArgument(beer.getBeerStyle() != null && !beer.getBeerStyle().trim().isEmpty(), "Invalid beerStyle");
		beer.setAverage(calculateAverage(beer.getMin(), beer.getMax()));
		try {
			beerRepository.save(beer);
		} catch (final DuplicateKeyException e) {
			throw new BeerAlreadyExistsException(e);
		}
	}
	
	protected int calculateAverage(final int min, final int max) {
		checkArgument(min <= max, "Invalid min and max values");
		int q = 0;
		int sum = 0;
		for (int i = min; i <= max; i++, q++) { 
			sum += i;
		}
		return sum / q;		
	}

	public Beer findById(final String id) {
		return beerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
	}
	
	public List<Beer> findAll() {
		return beerRepository.findAll();
	}
		
	public void delete(final String id) {
		beerRepository.deleteById(id);
	}
}
