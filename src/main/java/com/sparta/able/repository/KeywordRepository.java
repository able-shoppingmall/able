package com.sparta.able.repository;

import com.sparta.able.dto.keyword.res.KeywordRankDto;
import com.sparta.able.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    @Query(value = "WITH keyword_rank AS (SELECT keyword, RANK() OVER(ORDER BY used_count) AS rank FROM keywords) " +
            "SELECT * FROM keyword_rank LIMIT 10", nativeQuery = true)
    List<KeywordRankDto> findKeywordRank();
}
