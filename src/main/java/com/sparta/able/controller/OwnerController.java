package com.sparta.able.controller;

import com.sparta.able.dto.owner.req.OwnerLoginRequest;
import com.sparta.able.dto.owner.req.OwnerSignupRequest;
import com.sparta.able.dto.owner.res.OwnerResponse;
import com.sparta.able.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/owner")
public class OwnerController {

    private final OwnerService ownerService;

}
