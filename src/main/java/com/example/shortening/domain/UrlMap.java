package com.example.shortening.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(catalog = "store", name = "url_store")
public class UrlMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name = "short_url")
    private String shortUrl;

    public void setId(Long id) {
        this.id = id;
    }
}
