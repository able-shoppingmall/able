package com.sparta.able.controller;

import com.sparta.able.dto.user.req.UserLoginRequest;
import com.sparta.able.dto.user.req.UserSignupRequest;
import com.sparta.able.dto.user.res.UserResponse;
import com.sparta.able.service.UserService;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseBodyDto<UserResponse>> SignupUser(@RequestBody UserSignupRequest userSignupRequest) {
        UserResponse userResponse = userService.SignupUser(userSignupRequest);
        ResponseBodyDto<UserResponse> responseBody = ResponseBodyDto.success("회원가입 성공", userResponse);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBodyDto<UserResponse>> LoginUser(@RequestBody UserLoginRequest userLoginRequest){
        UserResponse userResponse = userService.LoginUser(userLoginRequest);
        ResponseBodyDto<UserResponse> responseBody = ResponseBodyDto.success("로그인 성공", userResponse);
        return ResponseEntity.ok(responseBody);
    }

}
