package com.example.shortening.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(catalog = "store", name = "url_count_board")
public class UrlCount {

    @Id
    private Long id;

    @Column(name = "request_count")
    private Long requestCount;

    public void increaseCount() {
        this.requestCount = requestCount + 1;
    }
}
