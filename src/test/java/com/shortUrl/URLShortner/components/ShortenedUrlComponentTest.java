package com.shortUrl.URLShortner.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShortenedUrlComponentTest {

    private final ShortenedUrlComponent shortenedUrlComponent = new ShortenedUrlComponent();

    @Test
    void generateShortenedUrlShouldSucceed() {

        String shortenedUrl = shortenedUrlComponent.generateShortenedUrl(DEFAULT_LENGTH, VALID_CHARS);

        assertEquals(DEFAULT_LENGTH, shortenedUrl.length());

        for (char c : shortenedUrl.toCharArray()) {
            assertTrue(VALID_CHARS.contains(String.valueOf(c)));
        }
    }

    @Test
    void generateShortenedUrlZeroKeyLengthShouldReturnEmptyString() {
        String shortenedUrl = shortenedUrlComponent.generateShortenedUrl(0, VALID_CHARS);

        assertEquals("", shortenedUrl);
    }

    @Test
    void generateShortenedUrlEmptyPermissibleCharsShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> shortenedUrlComponent.generateShortenedUrl(DEFAULT_LENGTH, ""));
    }

    private static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final int DEFAULT_LENGTH = 8;

}
