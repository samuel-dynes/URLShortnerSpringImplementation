package com.shortUrl.URLShortner.controllers;

import com.shortUrl.URLShortner.dtos.ShortenedUrlRequestDto;
import com.shortUrl.URLShortner.dtos.ShortenedUrlResponseDto;
import com.shortUrl.URLShortner.service.UrlShortenerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling URL shortening and expansion operations.
 * All endpoints are mapped under the base path "/urlapi/1.0/".
 */
@RestController
@RequestMapping("/urlapi/1.0/")
@Api(tags = "URL Shortener")
public class ShortenedUrlController {

    private final UrlShortenerService urlShortenerService;

    /**
     * Constructor to inject dependencies.
     *
     * @param urlShortenerService The service responsible for URL shortening operations.
     */
    public ShortenedUrlController(final UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * POST endpoint to shorten a URL.
     *
     * @param originalUrl The original URL to be shortened, provided in the request body.
     * @return ResponseEntity with the shortened URL if successful, or a bad request response if unsuccessful.
     */
    @PostMapping("/shorten")
    @ApiOperation(value = "Shorten URL",
                  notes = "Shortens the provided URL and returns a shortened URL that can be used to get the original"
                          + " URL.")
    @ApiResponses(value = { @ApiResponse(code = 200,
                                         message = "Returns the shortened URL that can be used to get the original "
                                                   + "URL."),
                            @ApiResponse(code = 400,
                                         message = "Failed to shorten the URL.") })
    public ResponseEntity<String> shortenUrl(@RequestBody @ApiParam(value = "Original URL to be shortened",
                                                                    required = true) String originalUrl) {
        ShortenedUrlResponseDto shortUrl = urlShortenerService.createShortUrl(new ShortenedUrlRequestDto(originalUrl));

        if (shortUrl != null) {
            return ResponseEntity.ok(shortUrl.getShortenedUrl());
        } else {
            return ResponseEntity.badRequest().body("Failed to shorten the URL.");
        }
    }

    /**
     * GET endpoint to expand a shortened URL.
     *
     * @param shortUrl The shortened URL key provided as a path variable.
     * @return ResponseEntity with the original URL if the expansion is successful,
     * or a bad request response if the entered shortened URL does not exist.
     */
    @GetMapping("/expand/{shortUrl}")
    @ApiOperation(value = "Expand Shortened URL",
                  notes = "Expands the provided shortened URL and returns the original URL.")
    @ApiResponses(value = { @ApiResponse(code = 200,
                                         message = "Returns the expanded URL."),
                            @ApiResponse(code = 400,
                                         message = "The entered shortened URL does not exist, ensure it has been "
                                                   + "entered correctly.") })
    public ResponseEntity<String> expandUrl(@PathVariable @ApiParam(value = "Shortened URL",
                                                                    required = true) String shortUrl) {
        String originalUrl = urlShortenerService.getFullUrl(shortUrl);

        if (originalUrl != null) {
            return ResponseEntity.ok(originalUrl);
        } else {
            return ResponseEntity.badRequest()
                                 .body("The entered shortened URL does not exist, ensure it has been entered "
                                       + "correctly.");
        }
    }
}
