package com.sparta.able.dto.owner.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class OwnerSignupRequest {

    //나중에 Exception 처리
    @Size(min = 2, max = 6)
    private String name;
    @Email
    private String email;
    //패턴 등록
    private String password;
    private String storeName;
}
