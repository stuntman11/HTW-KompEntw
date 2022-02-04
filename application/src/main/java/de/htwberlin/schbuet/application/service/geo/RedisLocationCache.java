package de.htwberlin.schbuet.application.service.geo;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import de.htwberlin.schbuet.application.configs.RedisConfig;
import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisLocationCache {
	private RedisConfig config;
	private ValueOperations<GeoAddress, GeoCoords> addressToCoords;
	private ValueOperations<GeoCoords, GeoAddress> coordsToAddress;

	public RedisLocationCache(RedisConfig config) {
		this.config = config;
		this.addressToCoords = config.createAddressToCoordsMapping();
		this.coordsToAddress = config.createCoordsToAddressMapping();
	}

	public void cacheAddressToCoords(GeoAddress address, GeoCoords coords) {
		try {
			addressToCoords.set(address, coords, config.getExpireInMillis(), TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("Failed to add geo coords to redis cache", e);
		}
	}

	public void cacheCoordsToAddress(GeoCoords coords, GeoAddress address) {
		try {
			coordsToAddress.set(coords, address, config.getExpireInMillis(), TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("Failed to add address to redis cache", e);
		}
	}

	public GeoCoords findCoords(GeoAddress address) {
		try {
			return addressToCoords.get(address);
		} catch (Exception e) {
			log.error("Failed to get geo coords from redis cache", e);
			return null;
		}
	}

	public GeoAddress findAddress(GeoCoords coords) {
		try {
			return coordsToAddress.get(coords);
		} catch (Exception e) {
			log.error("Failed to get address from redis cache", e);
			return null;
		}
	}
}
