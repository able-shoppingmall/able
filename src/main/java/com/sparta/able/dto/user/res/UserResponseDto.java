package com.sparta.able.dto.user.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private String name;
    private String email;
    private String token;
}
