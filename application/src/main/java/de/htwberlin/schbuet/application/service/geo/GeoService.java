package de.htwberlin.schbuet.application.service.geo;

import org.springframework.stereotype.Service;

import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import de.htwberlin.schbuet.application.errors.GeoLookupException;

@Service
public class GeoService {
	private GoogleMapsLocationProvider location;
	private RedisLocationCache locationCache;

	public GeoService(GoogleMapsLocationProvider location, RedisLocationCache locationCache) {
		this.location = location;
		this.locationCache = locationCache;
	}

	public GeoCoords getCoordsFromAddress(GeoAddress address) throws GeoLookupException {
		GeoCoords coords = locationCache.findCoords(address);

		if (coords != null) {
			return coords;
		}
		coords = location.getCoordsFromAddress(address);
		locationCache.cacheAddressToCoords(address, coords);
		return coords;
	}

	public GeoAddress getAddressFromCoords(GeoCoords coords) throws GeoLookupException {
		GeoAddress address = locationCache.findAddress(coords);

		if (address != null) {
			return address;
		}
		address = location.getAddressFromCoords(coords);
		locationCache.cacheCoordsToAddress(coords, address);
		return address;
	}
}
