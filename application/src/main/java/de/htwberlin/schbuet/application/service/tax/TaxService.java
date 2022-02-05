package de.htwberlin.schbuet.application.service.tax;

import de.htwberlin.schbuet.application.data.request.RequestTax;
import de.htwberlin.schbuet.application.errors.TaxCouldNotBeCalculatedException;

public interface TaxService {
	RequestTax getTaxForPrice(int priceInCents) throws TaxCouldNotBeCalculatedException;
}
