package com.shortUrl.URLShortner.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage(
                                                              "com.shortUrl.URLShortner.controllers"))
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("URL Shortener API",
                           "API for URL shortening and expansion operations",
                           "1.0",
                           "Terms of service",
                           new Contact("Samuel Dynes", "some_website_link", "your.email@example.com"),
                           "MIT License",
                           "https://opensource.org/license/mit/",
                           Collections.emptyList());
    }
}
