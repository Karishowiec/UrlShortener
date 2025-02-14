package com.goit.url_shortener.controller;

import com.goit.url_shortener.service.RedirectService;
import com.goit.url_shortener.url.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RedirectController {

    private final RedirectService redirectService;
    @Value("${app.base-url}")
    private String baseUrl;

    // Конструкторна ін'єкція
    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable("code") String code) {
        Optional<Url> url = redirectService.findUrlByShortUrl(String.format("%s/%s", baseUrl, code));

        if (url.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.FOUND) // HTTP 302 Redirect
                    .header("Location", url.get().getLongUrl())
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}