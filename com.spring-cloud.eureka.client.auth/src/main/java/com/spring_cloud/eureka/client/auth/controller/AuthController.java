package com.spring_cloud.eureka.client.auth.controller;

import com.spring_cloud.eureka.client.auth.dto.LoginRequestDto;
import com.spring_cloud.eureka.client.auth.dto.SignupRequestDto;
import com.spring_cloud.eureka.client.auth.dto.UsernameResponseDto;
import com.spring_cloud.eureka.client.auth.service.AuthService;
import com.spring_cloud.eureka.client.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(requestDto)));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UsernameResponseDto> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        UsernameResponseDto responseDto = userService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String access_token;
    }
}
