package br.com.alexandre.duff.web.repository;

import br.com.alexandre.duff.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends
  ReactiveCrudRepository<Beer, String>,
  ReactiveSortingRepository<Beer, String> {

}
