package com.sparta.able.service;

import com.sparta.able.dto.keyword.res.FindKeywordRankingResDto;
import com.sparta.able.dto.keyword.res.KeywordRankingDto;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

import static com.sparta.able.exception.ErrorCode.NOT_FOUND_CACHE;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordCacheService {
    private final CacheManager cacheManager;

    @Cacheable(value = "keywords", key = "#p0")
    public int addCache(String keyword) {
        return 0;
    }

    @CachePut(value = "keywords", key = "#p0")
    public int updateCache(String keyword, int currentUsedCount) {
        return currentUsedCount+1;
    }

    public ResponseBodyDto<FindKeywordRankingResDto> findKeyWordRank() {
        ConcurrentMapCache cache = (ConcurrentMapCache) cacheManager.getCache("keywords");

        if (cache == null) {
            throw new ApplicationException(NOT_FOUND_CACHE, NOT_FOUND_CACHE.getMessage());
        }

        ConcurrentMap<Object, Object> nativeCache = cache.getNativeCache();
        Set<Map.Entry<Object, Object>> cacheEntry = nativeCache.entrySet();

        PriorityQueue<Map.Entry<Object, Object>> sortedCacheEntry = new PriorityQueue<>(
                (a, b) -> Integer.compare((Integer) b.getValue(), (Integer) a.getValue())
        );
        for (Map.Entry<Object, Object> e : cacheEntry) {
            log.info("key = {}, value = {}", e.getKey(), e.getValue());
            sortedCacheEntry.offer(e);
        }

        List<KeywordRankingDto> foundRanks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (sortedCacheEntry.isEmpty()) break;

            KeywordRankingDto keywordRankingDto = KeywordRankingDto.make(
                    (String) sortedCacheEntry.poll().getKey(), i+1
            );
            foundRanks.add(keywordRankingDto);
        }

        FindKeywordRankingResDto keywordRankingsDto = FindKeywordRankingResDto.builder()
                .keywordRankings(foundRanks)
                .build();

        return ResponseBodyDto.success("검색어 순위 조회 성공", keywordRankingsDto);
    }
}
