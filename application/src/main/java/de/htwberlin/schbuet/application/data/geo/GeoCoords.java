package de.htwberlin.schbuet.application.data.geo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GeoCoords {
	private final double latitude;
	private final double longitude;
	
	public GeoCoords(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
