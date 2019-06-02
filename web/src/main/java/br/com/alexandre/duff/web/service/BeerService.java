package br.com.alexandre.duff.web.service;

import static com.google.common.base.Preconditions.checkArgument;

import br.com.alexandre.duff.domain.Beer;
import br.com.alexandre.duff.domain.Beer.ID;
import br.com.alexandre.duff.web.exception.BeerAlreadyExistsException;
import br.com.alexandre.duff.web.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BeerService {

  @Autowired
  private BeerRepository beerRepository;

  public Mono<ID> save(final Beer beer) {
    checkArgument(beer.getBeerStyle() != null && !beer.getBeerStyle().trim().isEmpty(), "Invalid beerStyle");
    beer.setAverage(calculateAverage(beer.getMin(), beer.getMax()));

    return beerRepository.save(beer).map(savedBeer -> new ID(savedBeer.getId()))
      .doOnError(DuplicateKeyException.class, e -> new BeerAlreadyExistsException(e));
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

  public Mono<Beer> findById(final String id) {
    return beerRepository.findById(id).
      switchIfEmpty(Mono.error(IllegalArgumentException::new));
  }

  public Flux<Beer> findAll() {
    return beerRepository.findAll();
  }

  public Mono<Void> delete(final String id) {
    return beerRepository.deleteById(id);
  }
}
