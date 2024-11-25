package com.sparta.able.dto.product.res;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder
public class SearchResultDto {
    private List<SearchProductResDto> content;
    private Pageable pageable;

    public static SearchResultDto make(List<SearchProductResDto> content, Pageable pageable) {
        return SearchResultDto.builder()
                .content(content)
                .pageable(pageable)
                .build();
    }
}
