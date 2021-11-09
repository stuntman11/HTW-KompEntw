package de.htwberlin.schbuet.calculator;

public class Tax {
	public final int basePrice;
	public final int tax;
	public final int priceWithTax;
	
	public Tax(int basePrice) {
		this.basePrice = basePrice;
		this.tax = (int)Math.ceil(basePrice * 0.19);
		this.priceWithTax = this.basePrice + this.tax;
	}
}
