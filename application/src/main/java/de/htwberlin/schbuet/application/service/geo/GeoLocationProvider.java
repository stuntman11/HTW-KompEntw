package de.htwberlin.schbuet.application.service.geo;

import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import de.htwberlin.schbuet.application.errors.GeoLookupException;

public interface GeoLocationProvider {
	GeoCoords getCoordsFromAddress(GeoAddress address) throws GeoLookupException;
	GeoAddress getAddressFromCoords(GeoCoords coords) throws GeoLookupException;
}
