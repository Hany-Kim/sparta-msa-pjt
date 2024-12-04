package com.spring_cloud.eureka.client.auth.service;

import com.spring_cloud.eureka.client.auth.dto.SignupRequestDto;
import com.spring_cloud.eureka.client.auth.dto.UserDto;
import com.spring_cloud.eureka.client.auth.dto.UsernameResponseDto;
import com.spring_cloud.eureka.client.auth.entity.User;
import com.spring_cloud.eureka.client.auth.entity.UserRoleEnum;
import com.spring_cloud.eureka.client.auth.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsernameResponseDto signUp(@Valid SignupRequestDto requestDto) {
        if(isExistUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        if(!isValidRole(requestDto.getRole())) {
            throw new IllegalArgumentException("유효하지 않는 역할입니다.");
        }

        User user = User.from(requestDto, passwordEncoder);
        userRepository.save(user);
        return new UsernameResponseDto(user.getUsername());
    }

    private boolean isValidRole(@NotBlank String role) {
        if(role.equals(String.valueOf(UserRoleEnum.CUSTOMER)) ||
                role.equals(String.valueOf(UserRoleEnum.OWNER))) {
            return true;
        }
        return false;
    }

    private boolean isExistUsername(
            @NotBlank @Size(min = 4, max = 20) @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username은 문자와 숫자로 구성되어야 합니다.") String username) {

        Optional<User> user = userRepository.findById(username);

        if(user.isPresent()) {
            return true;
        }

        return false;
    }

    public UserDto getUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserDto.from(user);
    }
}
