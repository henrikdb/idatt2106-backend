package no.ntnu.idi.stud.savingsapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation generation.
 * This class defines beans for configuring the OpenAPI documentation of the API.
 */
@Configuration
public class OpenAPIConfig {

  /**
   * Creates and configures the OpenAPI bean for the API.
   * @return The configured OpenAPI object representing the API documentation.
   */
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
        .info(new Info().title("Sparesti API").description("The Sparesti API").version("3.0"));
  }

  /**
   * Creates a SecurityScheme object representing the API key (Bearer token) scheme.
   * @return The configured SecurityScheme object for API key authentication.
   */
  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
  }
}
