package com.sparta.able.dto.keyword.res;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class KeywordRankingDto {
    private String keyword;
    private int rank;

    public static KeywordRankingDto make(String keyword, int rank) {
        return KeywordRankingDto.builder()
                .keyword(keyword)
                .rank(rank)
                .build();
    }
}
