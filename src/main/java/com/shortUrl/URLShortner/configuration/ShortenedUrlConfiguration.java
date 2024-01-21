package com.shortUrl.URLShortner.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to hold properties related to URL shortening.
 * These properties are loaded from the application configuration with the prefix "shortened-url".
 */
@ConfigurationProperties(prefix = "shortened-url")
@Configuration
@Getter
@Setter
public class ShortenedUrlConfiguration {

    /**
     * The set of characters allowed in the shortened URLs
     */
    private String permissableChars;

    /**
     * The length of the shortened URLs
     */
    private int length;

    /**
     * Domain and api path to expand shortened URL's
     */
    private String urlStart;
}
