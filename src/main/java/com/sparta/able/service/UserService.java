package com.sparta.able.service;

import com.sparta.able.config.PasswordEncoder;
import com.sparta.able.config.jwt.JwtUtil;
import com.sparta.able.dto.user.req.UserLoginRequest;
import com.sparta.able.dto.user.req.UserSignupRequest;
import com.sparta.able.dto.user.res.UserResponse;
import com.sparta.able.exception.AuthException;
import com.sparta.able.exception.InvalidRequestException;
import com.sparta.able.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.sparta.able.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponse SignupUser(UserSignupRequest usersignupRequest) {
        if(userRepository.existsByEmail(usersignupRequest.getEmail())){
            throw new InvalidRequestException("이미 존재하는 이메일입니다");
        }

        User user = new User(usersignupRequest.getName(), usersignupRequest.getEmail(), usersignupRequest.getPassword());

        userRepository.save(user);

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getName(), "ROLE_USER");

        return new UserResponse(user.getEmail(), user.getName(), token);
    }

}