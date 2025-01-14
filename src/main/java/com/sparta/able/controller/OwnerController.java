package com.sparta.able.controller;


import com.sparta.able.dto.owner.req.OwnerLoginRequestDto;
import com.sparta.able.dto.owner.req.OwnerSignupRequestDto;
import com.sparta.able.dto.owner.res.OwnerResponseDto;
import com.sparta.able.service.OwnerService;
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
@RequestMapping("api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<ResponseBodyDto<OwnerResponseDto>> SignupOwner(@Valid @RequestBody OwnerSignupRequestDto ownerSignupRequestDto) {
        OwnerResponseDto ownerResponseDto = ownerService.SignupOwner(ownerSignupRequestDto);
        ResponseBodyDto<OwnerResponseDto> responseBody = ResponseBodyDto.success("회원가입 성공", ownerResponseDto);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBodyDto<OwnerResponseDto>> LoginOwner(@RequestBody OwnerLoginRequestDto ownerLoginRequestDto){
        OwnerResponseDto ownerResponseDto = ownerService.LoginOwner(ownerLoginRequestDto);
        ResponseBodyDto<OwnerResponseDto> responseBody = ResponseBodyDto.success("로그인 성공", ownerResponseDto);
        return ResponseEntity.ok(responseBody);
    }
}
