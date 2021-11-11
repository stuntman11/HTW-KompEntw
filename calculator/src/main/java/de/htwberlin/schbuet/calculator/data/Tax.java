package de.htwberlin.schbuet.calculator.data;

import lombok.Data;

@Data
public class Tax {
	private int basePrice;
	private int includedTax;
	private int priceWithTax;
	private Double taxRate;

	private static final Double TAX_RATE = 0.19;

	public Tax(int basePriceInCent) {
        basePriceInCent = Math.abs(basePriceInCent);
		this.basePrice = basePriceInCent;
		this.includedTax = (int)Math.round(basePrice * TAX_RATE);
		this.priceWithTax = this.basePrice + this.includedTax;
		this.taxRate = TAX_RATE;
	}
}
