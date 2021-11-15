package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.body.TaxBody;

public interface CalculatorService {
	TaxBody getTaxForPrice(int priceInCents);
}
