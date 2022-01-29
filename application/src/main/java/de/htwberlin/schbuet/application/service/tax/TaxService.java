package de.htwberlin.schbuet.application.service.tax;

import de.htwberlin.schbuet.application.data.request.RequestTax;

public interface TaxService {
	RequestTax getTaxForPrice(int priceInCents);
}
