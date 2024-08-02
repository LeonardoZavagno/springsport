package com.springsport.core.controllers.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping
    @RequestMapping("/app-version")
    public Map<String, String> getStatus() {
        Map<String, String> map = new HashMap<>();
        map.put("app-version", appVersion);
        return map;
    }
}
