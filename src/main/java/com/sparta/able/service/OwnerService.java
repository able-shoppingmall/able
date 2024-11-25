package com.sparta.able.service;

import com.sparta.able.config.PasswordEncoder;
import com.sparta.able.config.jwt.JwtUtil;
import com.sparta.able.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


}
