package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.body.RequestTax;

public interface CalculatorService {
	RequestTax getTaxForPrice(int priceInCents);
}
