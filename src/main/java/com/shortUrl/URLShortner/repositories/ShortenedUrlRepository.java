package com.shortUrl.URLShortner.repositories;

import com.shortUrl.URLShortner.entities.ShortenedUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository used to access {@link ShortenedUrlEntity} objects stored in the DB
 */
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrlEntity, Long> {

    /**
     * Gets a {@link ShortenedUrlEntity} based on a given full URL
     *
     * @param fullUrl The full URL you are looking to access via a shortened link
     * @return the {@link ShortenedUrlEntity} associated with the given fullUrl
     */
    ShortenedUrlEntity findByFullUrl(String fullUrl);

    /**
     * Gets a {@link ShortenedUrlEntity} based on a given shortened URL
     *
     * @param shortenedUrl The shortened URL you are looking to access via a shortened link
     * @return the {@link ShortenedUrlEntity} associated with the given shortenedUrl
     */
    ShortenedUrlEntity findByShortenedUrl(String shortenedUrl);
}

