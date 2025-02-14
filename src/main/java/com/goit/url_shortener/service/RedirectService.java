package com.goit.url_shortener.service;

import com.goit.url_shortener.repository.UrlRepository;
import com.goit.url_shortener.url.Url;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedirectService {
    private final UrlRepository repository;

    // Конструкторна ін'єкція
    public RedirectService(UrlRepository repository) {
        this.repository = repository;
    }

    public Optional<Url> findUrlByShortUrl(String shortUrl) {
        return repository.findUrlByShortUrl(shortUrl);
    }
}