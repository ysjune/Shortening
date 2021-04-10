package com.example.shortening.domain;

import org.springframework.data.repository.Repository;

public interface UrlCountRepository extends Repository<UrlCount, Long> {

    UrlCount findById(Long id);

    UrlCount save(UrlCount urlCount);
}
