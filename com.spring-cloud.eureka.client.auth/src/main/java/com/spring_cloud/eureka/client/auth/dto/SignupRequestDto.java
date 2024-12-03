package com.spring_cloud.eureka.client.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username은 문자와 숫자로 구성되어야 합니다.")
    private String username;

    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[a-zA-Z\\d!@#$%^&*()_+\\-=]*$", message = "Password은 문자, 숫자, 특수기호로 구성되어야 합니다.")
    private String password;

    @NotBlank
    private String role;
}
