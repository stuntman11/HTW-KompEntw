package de.htwberlin.schbuet.application.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class GoogleMapsConfig {
	@Value("${googlemaps.apikey}")
	private String apikey;
	
	@Value("${googlemaps.language}")
	private String language;
	
	public String getApiKey() {
		return apikey;
	}
	
	public String getLanguage() {
		return language;
	}
}
