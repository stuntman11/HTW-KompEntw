package de.htwberlin.schbuet.application.service.tax;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.htwberlin.schbuet.application.data.request.RequestTax;

@Service
public class InternalTaxService implements TaxService {
	private final RestTemplate rest;
	
	public InternalTaxService() {
		this.rest = new RestTemplateBuilder()
				.rootUri("http://localhost:3000")
				.build();
	}
	
	@Override
	public RequestTax getTaxForPrice(int priceInCents) {
		String url = "/tax?priceInCents=" + priceInCents;
		return rest.getForObject(url, RequestTax.class);
	}
}
