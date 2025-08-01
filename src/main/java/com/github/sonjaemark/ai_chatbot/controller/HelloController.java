package com.github.sonjaemark.ai_chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloController {
    @GetMapping("/")
    public Mono<String> hello() {
        return Mono.just("Hello World!");
    }

    @GetMapping("/love")
    public Mono<String> love() {
        return Mono.just("Hello Love loveee! <3 <3 <3");
    }
}
