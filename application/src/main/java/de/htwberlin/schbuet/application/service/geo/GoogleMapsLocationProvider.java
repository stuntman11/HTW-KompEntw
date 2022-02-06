package de.htwberlin.schbuet.application.service.geo;

import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import de.htwberlin.schbuet.application.configs.GoogleMapsConfig;
import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import de.htwberlin.schbuet.application.errors.GeoLookupException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GoogleMapsLocationProvider implements GeoLocationProvider {
	private final GoogleMapsConfig config;
	private final GeoApiContext context;

	public GoogleMapsLocationProvider(GoogleMapsConfig config) {
		this.config = config;
		this.context = new GeoApiContext.Builder()
			.apiKey(config.getApiKey())
			.disableRetries()
			.build();
	}
	
	@Override
	public GeoCoords getCoordsFromAddress(GeoAddress address) throws GeoLookupException {
		String googleAddr = toGoogleAddressFormat(address);
		GeocodingResult geocode = fetchGoogleGeocode(googleAddr);
		LatLng location = geocode.geometry.location;
		return new GeoCoords(location.lat, location.lng);
	}

	@Override
	public GeoAddress getAddressFromCoords(GeoCoords coords) throws GeoLookupException {
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
	
	private GeocodingResult fetchGoogleGeocode(String address) throws GeoLookupException {
		GeocodingResult[] results;
		
		try {
			results = GeocodingApi.geocode(context, address)
					.language(config.getLanguage())
					.await();
		} catch (Exception e) {
			log.error("Failed to execute geocode on google maps");
			throw new GeoLookupException("Failed to execute geocode on google maps", e);
		}
		
		if (results.length == 0) {
			throw new GeoLookupException();
		}
		return results[0];
	}
	
	private GeocodingResult fetchGoogleReverseGeocode(LatLng location) throws GeoLookupException {
		GeocodingResult[] results;
		
		try {
			results = GeocodingApi.reverseGeocode(context, location)
					.language(config.getLanguage())
					.await();
		} catch (Exception e) {
			log.error("Failed to execute reverse geocode on google maps");
			throw new GeoLookupException("Failed to execute reverse geocode on google maps", e);
		}
		
		if (results.length == 0) {
			throw new GeoLookupException();
		}
		return results[0];
	}
	
	private String getAddressComponent(AddressComponentType type, GeocodingResult geocode) throws GeoLookupException {
		for (AddressComponent component : geocode.addressComponents) {
			for (AddressComponentType componentType : component.types) {
				if (componentType == type) {
					return component.longName;
				}
			}
		}
		log.error("Failed to find address component of type: " + type);
		throw new GeoLookupException("Failed to find address component of type: " + type);
	}
	
	private String toGoogleAddressFormat(GeoAddress address) {
		return address.getStreet() + ", "
			 + address.getCity() + ", "
			 + address.getPostalCode() + ", "
			 + address.getCountry();
	}
}
