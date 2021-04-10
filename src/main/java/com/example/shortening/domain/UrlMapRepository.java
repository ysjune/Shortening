package com.example.shortening.domain;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface UrlMapRepository extends Repository<UrlMap, Long> {

    UrlMap findByOriginUrl(String originUrl);

    List<UrlMap> findAll();

    UrlMap save(UrlMap targetUrlMap);

    UrlMap findByShortUrl(String shortUrl);
}
