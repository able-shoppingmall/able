package com.sparta.able.dto.owner.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class OwnerSignupRequestDto {

    @Size(min = 2, max = 6, message = "이름은 2자 이상 6자 이하로 작성하십시오.")
    private String name;
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 최소 8자, 문자와 숫자를 포함해야 합니다."
    )
    private String password;
    private String storeName;
}
