package com.sparta.able.dto.owner.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class OwnerLoginRequestDto {

    private String email;
    private String password;

}
