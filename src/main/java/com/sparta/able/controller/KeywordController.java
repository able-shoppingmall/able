package com.sparta.able.controller;

import com.sparta.able.dto.keyword.res.FindKeywordRankResDto;
import com.sparta.able.service.KeywordService;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping
    public ResponseEntity<ResponseBodyDto<FindKeywordRankResDto>> findKeywordRank() {
        ResponseBodyDto<FindKeywordRankResDto> responseBody = keywordService.findKeywordRank();

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
