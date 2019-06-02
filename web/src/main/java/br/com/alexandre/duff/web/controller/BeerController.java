package br.com.alexandre.duff.web.controller;

import br.com.alexandre.duff.domain.Beer.ID;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alexandre.duff.domain.Beer;
import br.com.alexandre.duff.web.service.BeerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

  private final BeerService beerService;

  @RequestMapping(path = "/beers", method = RequestMethod.POST,
    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
    consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public Mono<ID> create(@RequestBody final Beer beer) {
    return beerService.save(beer);
  }

  @RequestMapping(path = "/beers/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ResponseBody
  public Mono<Beer> findById(@PathVariable("id") final String id) {
    return beerService.findById(id);
  }

  @RequestMapping(path = "/beers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ResponseBody
  public Flux<Beer> findAll() {
    return beerService.findAll();
  }

  @RequestMapping(path = "/beers", method = RequestMethod.PUT,
    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
    consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public Mono<Beer.ID> update(@RequestBody final Beer beer) {
    return beerService.save(beer);
  }

  @RequestMapping(path = "/beers/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public Mono<Void> delete(@PathVariable("id") final String id) {
    return beerService.delete(id);
  }
}