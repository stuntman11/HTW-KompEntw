package de.htwberlin.schbuet.application.data.request;

import lombok.Data;

@Data
public class RequestTax {
	private int basePrice;
	private int includedTax;
	private int priceWithTax;
	private Double taxRate;
}
