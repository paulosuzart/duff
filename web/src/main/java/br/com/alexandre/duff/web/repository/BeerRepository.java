package br.com.alexandre.duff.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alexandre.duff.domain.Beer;

@Repository
public interface BeerRepository extends MongoRepository<Beer, String>, PagingAndSortingRepository<Beer, String> { }
