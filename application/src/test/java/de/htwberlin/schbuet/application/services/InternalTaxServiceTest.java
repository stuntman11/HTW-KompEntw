package de.htwberlin.schbuet.application.services;

import static org.hamcrest.text.MatchesPattern.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import de.htwberlin.schbuet.application.errors.TaxCouldNotBeCalculatedException;
import de.htwberlin.schbuet.application.service.tax.InternalTaxService;

@SpringBootTest
class InternalTaxServiceTest {
	@Autowired InternalTaxService taxCalculator;
	MockRestServiceServer taxRest;
	
	@BeforeEach
	void setUp() {
		taxRest = MockRestServiceServer.createServer(taxCalculator.getRestTemplate());
	}
	
	@Test
	void usesPriceAsUrlQuery() {
		taxRest.expect(ExpectedCount.once(), requestTo(matchesPattern(".*/tax\\?priceInCents=100")))
			.andRespond(withStatus(HttpStatus.OK));
		
		taxCalculator.getTaxForPrice(100);
		
		taxRest.verify();
	}
	
	@Test
	void throwsExceptionIfRequestFails() {
		taxRest.expect(ExpectedCount.once(), requestTo(matchesPattern(".*/tax\\?priceInCents=100")))
			.andRespond(withStatus(HttpStatus.BAD_GATEWAY));
		
		assertThrows(TaxCouldNotBeCalculatedException.class, () -> {
			taxCalculator.getTaxForPrice(100);
		});
		taxRest.verify();
	}
}
