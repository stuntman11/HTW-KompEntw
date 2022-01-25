package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.body.BodyTax;

public interface CalculatorService {
	BodyTax getTaxForPrice(int priceInCents);
}
