package de.htwberlin.schbuet.application.data.geo;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GeoAddress implements Serializable {
	private static final long serialVersionUID = -3007945157361609579L;
	
	private final String country;
	private final String city;
	private final String postalCode;
	private final String street;
	
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
