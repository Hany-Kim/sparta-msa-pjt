package com.spring_cloud.eureka.client.auth.service;

import com.spring_cloud.eureka.client.auth.dto.LoginRequestDto;
import com.spring_cloud.eureka.client.auth.entity.User;
import com.spring_cloud.eureka.client.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${service.jwt.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String createAccessToken(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .issuer(issuer)
                .issuedAt(new Date((System.currentTimeMillis())))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
