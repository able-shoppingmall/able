package com.sparta.able.controller;

import com.sparta.able.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping
    public void findKeywordRank() {

    }
}
