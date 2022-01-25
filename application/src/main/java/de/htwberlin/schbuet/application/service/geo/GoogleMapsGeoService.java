package de.htwberlin.schbuet.application.service.geo;

import java.io.IOException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import de.htwberlin.schbuet.application.exceptions.GeoServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsGeoService implements GeoService {
	private final GeoApiContext context;

	@Value("${geo.apikey}")
	private String apiKey;

	@Value("${geo.language}")
	private String language;
	
	public GoogleMapsGeoService() {
		this.context = new GeoApiContext.Builder()
			.apiKey(apiKey)
			.disableRetries()
			.build();
	}
	
	@Override
	public GeoCoords getCoordsFromAddress(GeoAddress address) throws GeoServiceException {
		String googleAddr = toGoogleAddressFormat(address);
		GeocodingResult geocode = fetchGoogleGeocode(googleAddr);
		LatLng location = geocode.geometry.location;
		return new GeoCoords(location.lat, location.lng);
	}

	@Override
	public GeoAddress getAddressFromCoords(GeoCoords coords) throws GeoServiceException {
		LatLng location = new LatLng(coords.getLatitude(), coords.getLongitude());
		GeocodingResult geocode = fetchGoogleReverseGeocode(location);
		String country = getAddressComponent(AddressComponentType.COUNTRY, geocode);
		String city = getAddressComponent(AddressComponentType.LOCALITY, geocode);
		String postalCode = getAddressComponent(AddressComponentType.POSTAL_CODE, geocode);
		String route = getAddressComponent(AddressComponentType.ROUTE, geocode);
		String streetNr = getAddressComponent(AddressComponentType.STREET_NUMBER, geocode);
		String street = route + " " + streetNr;
		return new GeoAddress(country, city, postalCode, street);
	}
	
	private GeocodingResult fetchGoogleGeocode(String address) throws GeoServiceException {
		GeocodingResult[] results;
		
		try {
			results = GeocodingApi.geocode(context, address).await();
		} catch (ApiException | InterruptedException | IOException e) {
			throw new GeoServiceException("Failed to execute geocode on google maps", e);
		}
		
		if (results.length == 0) {
			throw new GeoServiceException();
		}
		return results[0];
	}
	
	private GeocodingResult fetchGoogleReverseGeocode(LatLng location) throws GeoServiceException {
		GeocodingResult[] results;
		
		try {
			results = GeocodingApi.reverseGeocode(context, location).language(this.language).await();
		} catch (ApiException | InterruptedException | IOException e) {
			throw new GeoServiceException("Failed to execute reverse geocode on google maps", e);
		}
		
		if (results.length == 0) {
			throw new GeoServiceException();
		}
		return results[0];
	}
	
	private String getAddressComponent(AddressComponentType type, GeocodingResult geocode) throws GeoServiceException {
		for (AddressComponent component : geocode.addressComponents) {
			for (AddressComponentType componentType : component.types) {
				if (componentType == type) {
					return component.longName;
				}
			}
		}
		throw new GeoServiceException("Failed to find address component of type: " + type);
	}
	
	private String toGoogleAddressFormat(GeoAddress address) {
		return address.getStreet() + ", "
			 + address.getCity() + ", "
			 + address.getPostalCode() + ", "
			 + address.getCountry();
	}
}
