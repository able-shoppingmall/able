package com.sparta.able.dto.owner.req;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class OwnerLoginRequest {

    @Email
    private String email;
    //패턴 등록
    private String password;

}
