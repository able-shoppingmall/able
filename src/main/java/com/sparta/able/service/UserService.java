package com.sparta.able.service;

import com.sparta.able.config.PasswordEncoder;
import com.sparta.able.config.jwt.JwtUtil;
import com.sparta.able.dto.user.req.UserLoginRequestDto;
import com.sparta.able.dto.user.req.UserSignupRequestDto;
import com.sparta.able.dto.user.res.UserResponseDto;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;
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

    public UserResponseDto SignupUser(UserSignupRequestDto usersignupRequestDto) {
        if(userRepository.existsByEmail(usersignupRequestDto.getEmail())){
            throw new ApplicationException(ErrorCode.PRESENT_USER);
        }

        User user = new User(usersignupRequestDto.getName(), usersignupRequestDto.getEmail(), usersignupRequestDto.getPassword());

        userRepository.save(user);

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getName(), "ROLE_USER");

        return new UserResponseDto(user.getEmail(), user.getName(), token);
    }

    public UserResponseDto LoginUser(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail()).orElseThrow(
                ()-> new ApplicationException(ErrorCode.INCORRECT_FORMAT)
        );

        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.INCORRECT_FORMAT);
        }

        if(user.getDeletedAt() != null) {
            throw new ApplicationException(ErrorCode.DELETED_USER);
        }

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getName(), "ROLE_USER");

        return new UserResponseDto(user.getEmail(), user.getName(), token);
    }

}
