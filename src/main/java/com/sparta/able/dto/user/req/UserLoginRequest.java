package com.sparta.able.dto.user.req;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @Email
    private String email;
    //패턴 등록
    private String password;

}
