package com.example.shortening.domian;

import com.example.shortening.domain.UrlCount;
import com.example.shortening.domain.UrlCountRepository;
import com.example.shortening.domain.UrlMap;

import java.util.HashMap;
import java.util.Map;

public class MemoryUrlCountRepository implements UrlCountRepository {

    private Map<Long, UrlCount> maps = new HashMap<>();

    @Override
    public UrlCount findById(Long id) {
        return maps.get(id);
    }

    @Override
    public UrlCount save(UrlCount urlCount) {
        maps.put(urlCount.getId(), urlCount);
        return urlCount;
    }
}
