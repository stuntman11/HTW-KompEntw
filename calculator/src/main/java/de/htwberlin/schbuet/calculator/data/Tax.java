package de.htwberlin.schbuet.calculator.data;

import lombok.Data;

@Data
public class Tax {
	private int basePrice;
	private int includedTax;
	private int priceWithTax;
	private Double taxRate;

	public Tax(int basePriceInCent, Double taxRate) {
        basePriceInCent = Math.abs(basePriceInCent);
		this.basePrice = basePriceInCent;
		this.includedTax = (int)Math.round(basePrice * taxRate);
		this.priceWithTax = this.basePrice + this.includedTax;
		this.taxRate = taxRate;
	}
}
