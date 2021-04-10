package com.example.shortening.domian;

import com.example.shortening.domain.UrlMap;
import com.example.shortening.domain.UrlMapRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryUrlMapRepository implements UrlMapRepository {

    private long nextId = 1;
    private Map<Long, UrlMap> maps = new HashMap<>();

    @Override
    public UrlMap findByOriginUrl(String originUrl) {
        return maps.values().stream().filter(v -> v.getOriginUrl().equals(originUrl)).findFirst().orElse(null);
    }

    @Override
    public List<UrlMap> findAll() {
        return maps.values().stream().collect(Collectors.toList());
    }

    @Override
    public UrlMap save(UrlMap targetUrlMap) {
        if(targetUrlMap.getId() == null) {
            targetUrlMap.setId(nextId++);
        } else {
            if(nextId <= targetUrlMap.getId()) {
                nextId = targetUrlMap.getId() + 1;
            }
        }

        maps.put(targetUrlMap.getId(), targetUrlMap);
        return targetUrlMap;
    }

    @Override
    public UrlMap findByShortUrl(String shortUrl) {
        return maps.values().stream().filter(v -> v.getShortUrl().equals(shortUrl)).findFirst().orElse(null);
    }

    public void setNextId(long nextId) {
        this.nextId = nextId;
    }
}
