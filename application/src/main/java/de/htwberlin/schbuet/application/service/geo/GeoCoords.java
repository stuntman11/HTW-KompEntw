package de.htwberlin.schbuet.application.service.geo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GeoCoords {
	private double latitude;
	private double longitude;
	
	public GeoCoords(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
