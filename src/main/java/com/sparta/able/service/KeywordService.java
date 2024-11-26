package com.sparta.able.service;

import com.sparta.able.dto.keyword.res.FindKeywordRankResDto;
import com.sparta.able.dto.keyword.res.KeywordRankDto;
import com.sparta.able.repository.KeywordRepository;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public ResponseBodyDto<FindKeywordRankResDto> findKeywordRank() {
        List<KeywordRankDto> foundRanks = keywordRepository.findKeywordRank();
        FindKeywordRankResDto keywordRanksDto = FindKeywordRankResDto.builder()
                .keywordRanks(foundRanks)
                .build();

        return ResponseBodyDto.success("검색어 순위 조회 성공", keywordRanksDto);
    }
}
