package de.htwberlin.schbuet.application.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.htwberlin.schbuet.application.data.request.RequestTax;

@Service
public class InternalCalculatorService implements CalculatorService {
	private final RestTemplate rest;
	
	public InternalCalculatorService(RestTemplateBuilder restBuilder) {
		this.rest = restBuilder.rootUri("http://localhost:3000").build();
	}
	
	public RequestTax getTaxForPrice(int priceInCents) {
		String url = "/tax?priceInCents=" + priceInCents;
		return rest.getForObject(url, RequestTax.class);
	}
}
