package com.sparta.able.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KEYWORDS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Keyword extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "KEYWORD", nullable = false, unique = true)
    private String keyword;

    @Column(name = "USED_COUNT", nullable = false)
    @Builder.Default
    private int usedCount = 1;

    public static Keyword create(String keyword) {
        return Keyword.builder()
                .keyword(keyword)
                .build();
    }

    public void addUsedCount() {
        usedCount++;
    }
}
