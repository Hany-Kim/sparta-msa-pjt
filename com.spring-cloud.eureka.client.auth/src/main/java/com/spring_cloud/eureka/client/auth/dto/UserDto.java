package com.spring_cloud.eureka.client.auth.dto;

import com.spring_cloud.eureka.client.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String role;

    public static UserDto from(User user) {
        return  UserDto.builder()
                .username(user.getUsername())
                .role(user.getRole().toString())
                .build();
    }
}
