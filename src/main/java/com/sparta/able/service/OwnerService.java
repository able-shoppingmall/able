package com.sparta.able.service;

import com.sparta.able.config.PasswordEncoder;
import com.sparta.able.config.jwt.JwtUtil;
import com.sparta.able.dto.owner.req.OwnerLoginRequestDto;

import com.sparta.able.dto.owner.req.OwnerSignupRequestDto;
import com.sparta.able.dto.owner.res.OwnerResponseDto;
import com.sparta.able.entity.Owner;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;
import com.sparta.able.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public OwnerResponseDto SignupOwner(OwnerSignupRequestDto ownerSignupRequestDto) {
        if(ownerRepository.existsByEmail(ownerSignupRequestDto.getEmail())){
            throw new ApplicationException(ErrorCode.PRESENT_USER, "이미 존재하는 이메일입니다");
        }

        Owner owner = new Owner(ownerSignupRequestDto.getName(), ownerSignupRequestDto.getEmail(), ownerSignupRequestDto.getPassword(), ownerSignupRequestDto.getStoreName());

        ownerRepository.save(owner);

        String token = jwtUtil.createToken(owner.getId(), owner.getName(), owner.getEmail(),"ROLE_OWNER");

        return new OwnerResponseDto(owner.getName(), owner.getEmail(), owner.getStoreName(), token);

    }

    public OwnerResponseDto LoginOwner(OwnerLoginRequestDto ownerLoginRequestDto) {

        Owner owner = ownerRepository.findByEmail(ownerLoginRequestDto.getEmail()).orElseThrow(
                ()-> new ApplicationException(ErrorCode.INCORRECT_FORMAT, "이메일 또는 비밀번호가 일치하지 않습니다")
        );

        if(!passwordEncoder.matches(ownerLoginRequestDto.getPassword(), owner.getPassword())) {
            throw new ApplicationException(ErrorCode.INCORRECT_FORMAT, "이메일 또는 비밀번호가 일치하지 않습니다");
        }

        String token = jwtUtil.createToken(owner.getId(), owner.getName(), owner.getEmail(), "ROLE_OWNER");

        return new OwnerResponseDto(owner.getName(), owner.getEmail(), owner.getStoreName(), token);
    }
}
