package de.htwberlin.schbuet.calculator.data;

import lombok.Data;

@Data
public class Tax {
	private int basePrice;
	private int tax;
	private int priceWithTax;
	
	public Tax(int basePrice) {
		this.basePrice = basePrice;
		this.tax = (int)Math.ceil(basePrice * 0.19);
		this.priceWithTax = this.basePrice + this.tax;
	}
}
