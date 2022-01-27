package de.htwberlin.schbuet.application.service.geo;

import de.htwberlin.schbuet.application.errors.GeoServiceException;

public interface GeoService {
	GeoCoords getCoordsFromAddress(GeoAddress address) throws GeoServiceException;
	GeoAddress getAddressFromCoords(GeoCoords coords) throws GeoServiceException;
}
