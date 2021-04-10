package com.example.shortening;

import com.example.shortening.util.Base62Util;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class Base62UtilTest {

    @Test
    void 단축URL_생성시_8자리_이내() {

        String shortUrl = Base62Util.getShortUrl(Collections.emptyList());
        assertThat(shortUrl).isNotEqualTo("");
        assertThat(shortUrl.length()).isLessThanOrEqualTo(8);
    }
}
