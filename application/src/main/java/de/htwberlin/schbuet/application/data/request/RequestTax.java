package de.htwberlin.schbuet.application.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTax {
	private int basePrice;
	private int includedTax;
	private int priceWithTax;
	private Double taxRate;
}
