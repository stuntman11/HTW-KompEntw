package de.htwberlin.schbuet.application.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoogleMapsConfig {
	@Value("${googlemaps.apikey}")
	private String apiKey;
	
	@Value("${googlemaps.language}")
	private String language;
}
