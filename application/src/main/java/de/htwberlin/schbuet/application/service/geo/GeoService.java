package de.htwberlin.schbuet.application.service.geo;

public interface GeoService {
	GeoCoords getCoordsFromAddress(GeoAddress address) throws GeoServiceException;
	GeoAddress getAddressFromCoords(GeoCoords coords) throws GeoServiceException;
}
