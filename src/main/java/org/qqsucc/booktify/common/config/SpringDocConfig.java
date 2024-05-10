package org.qqsucc.booktify.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("!prod")
public class SpringDocConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Booktify APIs").version("v1.0.0"))
				.components(
						new Components().addSecuritySchemes(
								"bearer", new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")
										.in(SecurityScheme.In.HEADER)
										.name("Authorization")
						)
				)
				.addSecurityItem(
						new SecurityRequirement().addList("bearer", Arrays.asList("read", "write"))
				);
	}
}
