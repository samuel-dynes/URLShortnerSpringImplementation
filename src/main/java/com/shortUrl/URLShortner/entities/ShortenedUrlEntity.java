package com.shortUrl.URLShortner.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "urls")
public class ShortenedUrlEntity {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(unique = true)
    private String shortenedUrl;

    @Column(nullable = false)
    private String fullUrl;

    @Column(nullable = false)
    private Long usageCount;

    /**
     * Increments the number of times the shortenedURL has been used to access the full URL.
     */
    public void incrementUsageCount() {
        usageCount++;
    }
}
