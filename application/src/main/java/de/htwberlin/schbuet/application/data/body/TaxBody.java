package de.htwberlin.schbuet.application.data.body;

import lombok.Data;

@Data
public class TaxBody {
	private int basePrice;
	private int includedTax;
	private int priceWithTax;
	private Double taxRate;
}
