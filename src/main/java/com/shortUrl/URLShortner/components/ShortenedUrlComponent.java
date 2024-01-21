package com.shortUrl.URLShortner.components;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Component responsible for generating shortened URLs based on configuration settings.
 */
@Component
public class ShortenedUrlComponent {
    private final Random randomGenerator;

    /**
     * Constructor to inject dependencies.
     */
    public ShortenedUrlComponent() {
        this.randomGenerator = new Random();
    }

    /**
     * Generates a shortened URL based on the configured key length and allowed characters.
     *
     * @return A randomly generated shortened URL with the api + domain.
     */
    public String generateShortenedUrl(int length, String permissableChars) {

        StringBuilder shortenedUrlBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = randomGenerator.nextInt(permissableChars.length());
            shortenedUrlBuilder.append(permissableChars.charAt(randomIndex));
        }

        return shortenedUrlBuilder.toString();
    }
}
