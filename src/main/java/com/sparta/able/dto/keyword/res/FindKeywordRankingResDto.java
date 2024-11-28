package com.sparta.able.dto.keyword.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FindKeywordRankingResDto {
    private List<KeywordRankingDto> keywordRankings;
}
