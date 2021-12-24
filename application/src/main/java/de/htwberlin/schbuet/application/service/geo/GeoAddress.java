package de.htwberlin.schbuet.application.service.geo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GeoAddress {
	private String country;
	private String city;
	private String postalCode;
	private String street;
	
	public GeoAddress(String country, String city, String postalCode, String street) {
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.street = street;
	}
	
	public String getAddressShort() {
		return street + ", " + postalCode + " " + city;
	}
}
