package com.sparta.able.service;

import com.sparta.able.config.PasswordEncoder;
import com.sparta.able.config.jwt.JwtUtil;
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
            throw new ApplicationException(ErrorCode.PRESENT_USER);
        }

        Owner owner = new Owner(ownerSignupRequestDto.getName(), ownerSignupRequestDto.getEmail(), ownerSignupRequestDto.getPassword(), ownerSignupRequestDto.getStoreName());

        ownerRepository.save(owner);

        String token = jwtUtil.createToken(owner.getId(), owner.getEmail(), owner.getName(), "ROLE_OWNER");

        return new OwnerResponseDto(owner.getEmail(), owner.getName(), owner.getStoreName(), token);
    }
}
