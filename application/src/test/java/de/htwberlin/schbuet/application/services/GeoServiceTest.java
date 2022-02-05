package de.htwberlin.schbuet.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import de.htwberlin.schbuet.application.errors.GeoLookupException;
import de.htwberlin.schbuet.application.service.geo.GeoService;
import de.htwberlin.schbuet.application.service.geo.GoogleMapsLocationProvider;
import de.htwberlin.schbuet.application.service.geo.RedisLocationCache;

@SpringBootTest
class GeoServiceTest {
	@MockBean GoogleMapsLocationProvider location;
	@MockBean RedisLocationCache locationCache;
	GeoService geo;

	final GeoCoords COORDS = new GeoCoords(52.612, 6.541);
	final GeoAddress ADDRESS = new GeoAddress("Deutschland", "Berlin", "10178", "Alexanderplatz 1");

	@BeforeEach
    void setUp(){
		geo = new GeoService(location, locationCache);
    }
	
	@Test
	void requestsAddressFromProviderAndCacheFails() throws GeoLookupException {
		when(location.getAddressFromCoords(COORDS)).thenReturn(ADDRESS);
		when(locationCache.findAddress(any())).thenReturn(null);
		
		GeoAddress address = geo.getAddressFromCoords(COORDS);
		
		assertEquals(ADDRESS, address);
		verify(locationCache).findAddress(COORDS);
		verify(location).getAddressFromCoords(COORDS);
		verify(locationCache).cacheCoordsToAddress(COORDS, ADDRESS);
	}
	
	@Test
	void requestsAddressFromProviderAndCacheSucceeds() throws GeoLookupException {
		when(location.getAddressFromCoords(COORDS)).thenReturn(ADDRESS);
		when(locationCache.findAddress(COORDS)).thenReturn(ADDRESS);
		
		GeoAddress address = geo.getAddressFromCoords(COORDS);
		
		assertEquals(ADDRESS, address);
		verify(location, never()).getAddressFromCoords(COORDS);
		verify(locationCache, never()).cacheCoordsToAddress(COORDS, ADDRESS);
	}
	
	@Test
	void requestsCoordsFromProviderAndCacheFails() throws GeoLookupException {
		when(location.getCoordsFromAddress(ADDRESS)).thenReturn(COORDS);
		when(locationCache.findCoords(any())).thenReturn(null);
		
		GeoCoords coords = geo.getCoordsFromAddress(ADDRESS);
		
		assertEquals(COORDS, coords);
		verify(locationCache).findCoords(ADDRESS);
		verify(location).getCoordsFromAddress(ADDRESS);
		verify(locationCache).cacheAddressToCoords(ADDRESS, COORDS);
	}
	
	@Test
	void requestsCoordsFromProviderAndCacheSucceeds() throws GeoLookupException {
		when(location.getCoordsFromAddress(ADDRESS)).thenReturn(COORDS);
		when(locationCache.findCoords(ADDRESS)).thenReturn(COORDS);
		
		GeoCoords coords = geo.getCoordsFromAddress(ADDRESS);
		
		assertEquals(COORDS, coords);
		verify(location, never()).getCoordsFromAddress(ADDRESS);
		verify(locationCache, never()).cacheAddressToCoords(ADDRESS, COORDS);
	}
	
	@Test
	void requestsCoordsFromDisconnectedProvider() throws GeoLookupException {
		when(location.getCoordsFromAddress(ADDRESS)).thenThrow(GeoLookupException.class);
		when(locationCache.findCoords(any())).thenReturn(null);
		
		assertThrows(
				GeoLookupException.class,
				() -> geo.getCoordsFromAddress(ADDRESS)
		);
	}
}
