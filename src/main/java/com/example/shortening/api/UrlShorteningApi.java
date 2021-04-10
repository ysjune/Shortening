package com.example.shortening.api;

import com.example.shortening.dto.ApiResponse;
import com.example.shortening.dto.UrlDTO;
import com.example.shortening.dto.UrlParam;
import com.example.shortening.service.UrlShorteningService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UrlShorteningApi {

    private final UrlShorteningService service;

    public UrlShorteningApi(UrlShorteningService service) {
        this.service = service;
    }

    @PostMapping(value = "/urls")
    public ApiResponse svaeUrls(@RequestBody UrlParam param) {
        try {
            UrlDTO urlDTO = service.saveShortenUrl(param.getOriginUrl());
            return new ApiResponse().okWithData(urlDTO);
        } catch (Exception e) {
            return new ApiResponse().failWithMessage(e.getMessage());
        }
    }

    @GetMapping(value = "/{shortUrl}")
    public RedirectView getOriginUrl(@PathVariable("shortUrl") String shortUrl) {
            RedirectView redirectView = new RedirectView();
            try {
                redirectView.setUrl(service.getOriginUrl(shortUrl));
                return redirectView;
            } catch (Exception e) {
                redirectView.setUrl("/error");
                return redirectView;
            }
    }
}
