package com.example.shortening.service;

import com.example.shortening.domain.UrlCount;
import com.example.shortening.domain.UrlCountRepository;
import com.example.shortening.domain.UrlMap;
import com.example.shortening.domain.UrlMapRepository;
import com.example.shortening.dto.UrlDTO;
import com.example.shortening.util.Base62Util;
import com.example.shortening.util.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Service
public class UrlShorteningService {

    private final UrlMapRepository urlMapRepository;

    private final UrlCountRepository urlCountRepository;

    private final String hostUrl;

    public UrlShorteningService(UrlMapRepository urlMapRepository,
                                UrlCountRepository urlCountRepository,
                                @Value("${hostUrl}") String hostUrl) {
        this.urlMapRepository = urlMapRepository;
        this.urlCountRepository = urlCountRepository;
        this.hostUrl = hostUrl;
    }

    @Transactional
    public UrlDTO saveShortenUrl(String originUrl) {

        String errors = UrlValidator.validate(originUrl);
        if(!errors.isEmpty()) {
            throw new RuntimeException(errors);
        }

        try {
            UrlMap urlMap = urlMapRepository.findByOriginUrl(originUrl);
            if(!ObjectUtils.isEmpty(urlMap)) {

                UrlCount urlCount = urlCountRepository.findById(urlMap.getId());
                urlCount.increaseCount();

                return UrlDTO.builder().url(hostUrl + urlMap.getShortUrl()).count(urlCount.getRequestCount()).build();
            }

            String shortUrl = Base62Util.getShortUrl(
                    urlMapRepository.findAll().stream().map(v -> v.getShortUrl()).collect(Collectors.toList()));
            if(shortUrl.isEmpty()) {
                throw new Exception("단축 URL 을 생성할 수 없습니다.");
            }

            UrlMap save = urlMapRepository.save(UrlMap.builder().originUrl(originUrl).shortUrl(shortUrl).build());
            UrlCount urlCount = urlCountRepository.save(UrlCount.builder().id(save.getId()).requestCount(1L).build());

            return UrlDTO.builder().url(hostUrl + save.getShortUrl()).count(urlCount.getRequestCount()).build();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getOriginUrl(String shortUrl) {
        UrlMap urlMap = urlMapRepository.findByShortUrl(shortUrl);
        if(ObjectUtils.isEmpty(urlMap)) {
            throw new RuntimeException("존재하지 않는 단축 URL 입니다.");
        }
        return urlMap.getOriginUrl();
    }
}
