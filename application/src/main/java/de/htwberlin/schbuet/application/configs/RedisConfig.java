package de.htwberlin.schbuet.application.configs;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RedisConfig {
	@Value("${redis.hostname}")
	private String hostname;

	@Value("${redis.port}")
	private int port;

	@Value("${redis.timeout:200}") //Default: 200 millis
	private long timeoutInMillis;

	@Value("${redis.expire:86400000}") //Default: 24 hours
	private long expireInMillis;
	
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofMillis(timeoutInMillis))
				.build();

		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(hostname, port), clientConfig);
	}

	@Bean
	public ValueOperations<GeoAddress, GeoCoords> createAddressToCoordsMapping() {
		var template = new RedisTemplate<GeoAddress, GeoCoords>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(RedisSerializer.java());
		template.setValueSerializer(RedisSerializer.java());
		template.afterPropertiesSet();
		return template.opsForValue();
	}

	@Bean
	public ValueOperations<GeoCoords, GeoAddress> createCoordsToAddressMapping() {
		var template = new RedisTemplate<GeoCoords, GeoAddress>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(RedisSerializer.java());
		template.setValueSerializer(RedisSerializer.java());
		template.afterPropertiesSet();
		return template.opsForValue();
	}
}
