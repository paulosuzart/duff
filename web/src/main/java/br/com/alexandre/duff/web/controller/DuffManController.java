package br.com.alexandre.duff.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Preconditions.checkArgument;

import br.com.alexandre.duff.domain.DuffMan;
import br.com.alexandre.duff.domain.Temperature;
import br.com.alexandre.duff.web.service.DuffManService;

@RestController
public class DuffManController {

	@Autowired
	private DuffManService duffManService;
	
	@RequestMapping(path = "/duffman", method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public DuffMan.Opinion askOpinion(@RequestBody final Temperature temperature) {
		checkArgument(temperature.getTemperature() != null, "Invalid temperature");
		return duffManService.askOpinion(temperature);
	}
}
