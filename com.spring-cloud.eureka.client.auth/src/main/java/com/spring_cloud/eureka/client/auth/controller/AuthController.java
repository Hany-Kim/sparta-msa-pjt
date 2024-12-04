package com.spring_cloud.eureka.client.auth.controller;

import com.spring_cloud.eureka.client.auth.dto.SignupRequestDto;
import com.spring_cloud.eureka.client.auth.dto.UsernameResponseDto;
import com.spring_cloud.eureka.client.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    public final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UsernameResponseDto> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        UsernameResponseDto responseDto = userService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 로그인 API처리
    // 로그인 API는 JWT 토큰을 발급해주는 역할을 합니다.
    // 로그인 API는 /auth/sign-in 경로로 POST 요청을 받습니다.
    // 로그인 API는 로그인 요청을 받아서 로그인을 처리하고, JWT 토큰을 발급해줍니다.
    // 코드 작성해
    // 로그인 API를 구현해보겠습니다. 로그인 API는 /auth/sign-in 경로로 POST 요청을 받습니다.
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn() {

        return ResponseEntity.status(HttpStatus.OK).body("signIn");
    }
}
