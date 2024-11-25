package com.sparta.able.controller;

import com.sparta.able.dto.user.req.UserLoginRequestDto;
import com.sparta.able.dto.user.req.UserSignupRequestDto;
import com.sparta.able.dto.user.res.UserResponseDto;
import com.sparta.able.service.UserService;
import com.sparta.able.util.ResponseBodyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseBodyDto<UserResponseDto>> SignupUser(@Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
        UserResponseDto userResponseDto = userService.SignupUser(userSignupRequestDto);
        ResponseBodyDto<UserResponseDto> responseBody = ResponseBodyDto.success("회원가입 성공", userResponseDto);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBodyDto<UserResponseDto>> LoginUser(@RequestBody UserLoginRequestDto userLoginRequestDto){
        UserResponseDto userResponseDto = userService.LoginUser(userLoginRequestDto);
        ResponseBodyDto<UserResponseDto> responseBody = ResponseBodyDto.success("로그인 성공", userResponseDto);
        return ResponseEntity.ok(responseBody);
    }

}
