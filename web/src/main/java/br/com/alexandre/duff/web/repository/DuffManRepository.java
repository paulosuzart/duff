package br.com.alexandre.duff.web.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import br.com.alexandre.duff.domain.DuffMan.Classification;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import br.com.alexandre.duff.domain.Beer;
import br.com.alexandre.duff.domain.DuffMan;
import br.com.alexandre.duff.domain.Temperature;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class DuffManRepository {

  private static final String BEER_COLLECTION = "beer";

  @Autowired
  private ReactiveMongoTemplate mongoTemplate;

  @Value("${duffman.limit.classification:4}")
  private int limit;

  private Logger logger = LoggerFactory.getLogger(DuffManRepository.class);

  public Flux<Classification> classificateBeers(final Temperature temperature) {
    final Integer value = temperature.getTemperature();
    logger.info("Running classificate beers using temperature: '{}'", value);
    final MatchOperation match = match(Criteria
      .where("min").lte(value)
      .and("max").gte(value)
      .and("average").exists(true));

    final ProjectionOperation projection = project("beerStyle")
      .andExpression("abs(average - [0])", value).as("ranking");

    final SortOperation sort = sort(Direction.ASC, "ranking", "beerStyle");

    final LimitOperation limitOperation = limit(limit);

    final TypedAggregation<Beer> typedAggregation = newAggregation(Beer.class, match, projection, sort, limitOperation);

    Flux<Classification> aggregate = mongoTemplate.aggregate(typedAggregation, BEER_COLLECTION, Classification.class);

    return aggregate.collectList().map(classifications -> {
      logger.debug("Classification returned '{}' candidates", classifications.size());
      Classification firstClassification = classifications.get(0);
      return classifications.stream()
        .filter(c -> c.getRanking().equals(firstClassification.getRanking()))
        .collect(Collectors.toList());

    }).flatMapMany(Flux::fromIterable);

  }

}
