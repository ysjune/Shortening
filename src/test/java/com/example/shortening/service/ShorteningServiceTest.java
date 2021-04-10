package com.example.shortening.service;

import com.example.shortening.domain.UrlCount;
import com.example.shortening.domain.UrlMap;
import com.example.shortening.domian.MemoryUrlCountRepository;
import com.example.shortening.domian.MemoryUrlMapRepository;
import com.example.shortening.dto.UrlDTO;
import com.example.shortening.util.Base62Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ShorteningServiceTest {

    private UrlShorteningService service;
    private final MemoryUrlMapRepository urlMapRepository = new MemoryUrlMapRepository();
    private final MemoryUrlCountRepository urlCountRepository = new MemoryUrlCountRepository();
    @Mock
    private Base62Util base62Util;

    @BeforeEach
    void setUp() {
        service = new UrlShorteningService(urlMapRepository, urlCountRepository);
    }

    @Test
    void URL이_공백일때_validate_에러() {
        assertThatThrownBy(() -> service.saveShortenUrl("")).hasMessageContaining("URL은 공백일 수 없습니다.");
    }

    @Test
    void URL에_공백이_들어가있을때_validate_에러() {
        assertThatThrownBy(() -> service.saveShortenUrl("https://www.google.com/aaa /bbb")).hasMessageContaining("URL 사이에 공백이 들어갈 수 없습니다.");
    }

    @Test
    void URL형식이_올바르지않을떄_validate_에러() {
        assertThatThrownBy(() -> service.saveShortenUrl("www.google.com/aaa/bbb")).hasMessageContaining("URL 형식이 맞지 않습니다.");
    }

    @Test
    void 단축URL을_생성() {

        UrlDTO urlDTO = service.saveShortenUrl("https://en.wikipedia.org/wiki/URL_shortening");
        assertThat(urlDTO.getCount()).isEqualTo(1);
        assertThat(urlDTO.getUrl().length()).isLessThanOrEqualTo(8);
    }

    @Test
    void 단축URL을생성_후_재호출했을떄_카운트증가() {

        UrlDTO urlDTO1 = service.saveShortenUrl("https://en.wikipedia.org/wiki/URL_shortening2");
        UrlDTO urlDTO2 = service.saveShortenUrl("https://en.wikipedia.org/wiki/URL_shortening2");

        assertThat(urlDTO1.getUrl()).isEqualTo(urlDTO2.getUrl());
        assertThat(urlDTO1.getCount()).isEqualTo(1);
        assertThat(urlDTO2.getCount()).isEqualTo(2);
    }

    @Test
    void 제대로된URL을_전송했으나_base62에서_생성못함() {

        MockedStatic<Base62Util> mockedStatic = mockStatic(Base62Util.class);
        try {
            given(Base62Util.getShortUrl(anyList())).willReturn("");
            assertThatThrownBy(() -> service.saveShortenUrl("https://en.wikipedia.org/wiki/URL_shortening"))
                    .hasMessageContaining("단축 URL 을 생성할 수 없습니다.");
        } finally {
            mockedStatic.close();
        }
    }

    @Test
    void 이미생성된URL호출시_카운트_증가() {
        UrlMap urlMap = urlMapRepository.save(UrlMap.builder().shortUrl("As2sE").originUrl("https://en.wikipedia.org/wiki/URL_shortening").build());
        urlCountRepository.save(UrlCount.builder().id(urlMap.getId()).requestCount(1L).build());

        UrlDTO urlDTO = service.saveShortenUrl("https://en.wikipedia.org/wiki/URL_shortening");
        assertThat(urlDTO.getCount()).isEqualTo(2);
        assertThat(urlDTO.getUrl()).isEqualTo("As2sE");
    }

    @Test
    void 존재하지않는_단축URL_호출() {
        UrlMap urlMap = urlMapRepository.save(UrlMap.builder().shortUrl("E90Ad").originUrl("https://en.wikipedia.org/wiki/URL_shortening").build());
        urlCountRepository.save(UrlCount.builder().id(urlMap.getId()).requestCount(1L).build());

        assertThatThrownBy(() -> service.getOriginUrl("E112as"))
                .hasMessageContaining("존재하지 않는 단축 URL 입니다.");
    }

    @Test
    void 단축URL_호출() {
        UrlMap urlMap = urlMapRepository.save(UrlMap.builder().shortUrl("E90Ad").originUrl("https://en.wikipedia.org/wiki/URL_shortening").build());
        urlCountRepository.save(UrlCount.builder().id(urlMap.getId()).requestCount(1L).build());

        String originUrl = service.getOriginUrl("E90Ad");
        assertThat(originUrl).isEqualTo("https://en.wikipedia.org/wiki/URL_shortening");
    }
}
