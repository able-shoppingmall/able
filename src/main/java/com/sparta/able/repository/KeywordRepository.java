package com.sparta.able.repository;

import com.sparta.able.dto.keyword.res.KeywordRankDto;
import com.sparta.able.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    @Query(value =
            "SELECT k.keyword AS keyword, RANK() OVER(ORDER BY k.usedCount DESC) AS rank " +
            "FROM Keyword AS k ORDER BY 2 LIMIT 10"
    )
    List<KeywordRankDto> findKeywordRank();

    Keyword findByKeyword(String keyword);
}
