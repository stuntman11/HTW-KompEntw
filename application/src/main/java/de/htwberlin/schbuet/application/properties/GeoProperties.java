package de.htwberlin.schbuet.application.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "geo")
@Data
public class GeoProperties {
    String apiKey;
    String language;
}