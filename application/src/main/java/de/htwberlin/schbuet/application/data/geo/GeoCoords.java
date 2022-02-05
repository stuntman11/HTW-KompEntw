package de.htwberlin.schbuet.application.data.geo;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GeoCoords implements Serializable {
	private static final long serialVersionUID = -4381157297932513497L;
	
	private final double latitude;
	private final double longitude;
	
	public GeoCoords(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
