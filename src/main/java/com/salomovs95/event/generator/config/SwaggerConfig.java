package com.salomovs95.event.generator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    Info info = new Info();
    OpenAPI openApi = new OpenAPI();
    return openApi.info(info
      .title("Events Manager API")
      .version("0.1.0")
      .description("This is the Event's Manager API documented with Swagger and SpringDoc.")
    );
  }
}
