package de.htwberlin.schbuet.application.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
    	Info info = new Info()
		    	.title("Main demo application API")
		        .description("Spring shop sample application")
		        .version("v1.0.0")
		        .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    	
        List<Server> servers = List.of(
        		new Server().url("http://127.0.0.1")
        );

        ExternalDocumentation externalDocs = new ExternalDocumentation()
	            .description("Wiki Documentation")
	            .url("https://github.com/stuntman11/HTW-KompEntw");
        
        return new OpenAPI()
                .info(info)
                .servers(servers)
                .externalDocs(externalDocs);
    }
}
