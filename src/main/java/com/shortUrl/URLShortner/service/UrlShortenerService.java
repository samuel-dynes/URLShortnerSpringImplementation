package com.shortUrl.URLShortner.service;

import com.shortUrl.URLShortner.components.ShortenedUrlComponent;
import com.shortUrl.URLShortner.configuration.ShortenedUrlConfiguration;
import com.shortUrl.URLShortner.dtos.ShortenedUrlRequestDto;
import com.shortUrl.URLShortner.dtos.ShortenedUrlResponseDto;
import com.shortUrl.URLShortner.entities.ShortenedUrlEntity;
import com.shortUrl.URLShortner.repositories.ShortenedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Service responsible for URL shortening and expansion operations.
 */
@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final ShortenedUrlRepository repository;
    private final ShortenedUrlComponent component;
    @Qualifier("shortened-url-com.shortUrl.URLShortner.configuration.ShortenedUrlConfiguration")
    private final ShortenedUrlConfiguration configuration;

    /**
     * Creates a shortened URL based on the provided full URL.
     *
     * @param request The DTO containing the full URL to be shortened.
     * @return ShortenedUrlResponseDto containing the shortened URL, or an existing shortened URL if it already exists.
     */
    public ShortenedUrlResponseDto createShortUrl(ShortenedUrlRequestDto request) {
        String fullUrl = URLDecoder.decode(request.getFullUrl(), StandardCharsets.UTF_8);
        ShortenedUrlEntity existingShortUrl = repository.findByFullUrl(fullUrl);

        if (existingShortUrl != null) {
            // If the full URL already has a shortened version, return the existing shortened URL.
            return new ShortenedUrlResponseDto(existingShortUrl.getShortenedUrl());
        } else {
            // Generate a new shortened URL, save it to the repository, and return the new shortened URL.
            String shortenedUrl = component.generateShortenedUrl(configuration.getLength(),
                                                                 configuration.getPermissableChars());
            ShortenedUrlEntity newEntity = ShortenedUrlEntity.builder()
                                                             .shortenedUrl(shortenedUrl)
                                                             .fullUrl(fullUrl)
                                                             .usageCount(0L)
                                                             .build();
            repository.save(newEntity);
            return new ShortenedUrlResponseDto(configuration.getUrlStart() + shortenedUrl);
        }
    }

    /**
     * Expands a shortened URL to its original full URL and increments the usage count.
     *
     * @param shortenedUrl The shortened URL key.
     * @return The original full URL if the shortened URL exists, or null if not found.
     */
    public String getFullUrl(String shortenedUrl) {
        ShortenedUrlEntity alreadyPopulatedEntity = repository.findByShortenedUrl(shortenedUrl);
        if (alreadyPopulatedEntity != null) {
            // Increment the usage count and save the updated entity.
            alreadyPopulatedEntity.incrementUsageCount();
            repository.save(alreadyPopulatedEntity);

            // Decode the URL before returning
            return URLDecoder.decode(alreadyPopulatedEntity.getFullUrl(), StandardCharsets.UTF_8);
        }

        // Return null if the shortened URL does not exist.
        return null;
    }
}
