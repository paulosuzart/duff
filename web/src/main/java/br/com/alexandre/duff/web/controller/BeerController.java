package br.com.alexandre.duff.web.controller;

import java.util.List;

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

@RestController
public class BeerController {

	@Autowired
	private BeerService beerService;
	
	@RequestMapping(path = "/beers", method = RequestMethod.POST, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Beer.ID create(@RequestBody final Beer beer) {
		beerService.save(beer);
		return new Beer.ID(beer.getId());
	}
		
	@RequestMapping(path = "/beers/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public Beer findById(@PathVariable("id") final String id) {
		return beerService.findById(id);
	}
	
	@RequestMapping(path = "/beers", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public List<Beer> findAll() {
		return beerService.findAll();
	}

	@RequestMapping(path = "/beers", method = RequestMethod.PUT, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Beer.ID update(@RequestBody final Beer beer) {
		beerService.save(beer);
		return new Beer.ID(beer.getId());
	}

	@RequestMapping(path = "/beers/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable("id") final String id) {
		beerService.delete(id);
	}
}