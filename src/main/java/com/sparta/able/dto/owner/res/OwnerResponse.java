package com.sparta.able.dto.owner.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerResponse {

    private String name;
    private String email;
    private String storeName;
    private String token;
}