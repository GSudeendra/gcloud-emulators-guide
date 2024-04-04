package com.gcloud.emulators.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    // GET endpoint
    @GetMapping("/hello")
    public String testGet() {
        return "Hello, World!";
    }

    // POST endpoint
    @PostMapping("/hello")
    public String testPost(@RequestBody String name) {
        return "Hello, " + name + "!";
    }
}