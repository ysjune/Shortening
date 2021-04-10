package com.example.shortening.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainApi {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
