package de.htwberlin.schbuet.application.service.geo;

import org.springframework.stereotype.Service;

import de.htwberlin.schbuet.application.configs.GoogleMapsConfig;
import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import de.htwberlin.schbuet.application.errors.GeoLookupException;

@Service
public class GeoService {
	private GeoLocationProvider location;
	
	public GeoService(GoogleMapsConfig googleConfig) {
		this.location = new GoogleMapsLocationProvider(googleConfig.getApiKey(), googleConfig.getLanguage());
	}
	
	public GeoCoords getCoordsFromAddress(GeoAddress address) throws GeoLookupException {
		return location.getCoordsFromAddress(address);
	}
	
	public GeoAddress getAddressFromCoords(GeoCoords coords) throws GeoLookupException {
		return location.getAddressFromCoords(coords);
	}
}
