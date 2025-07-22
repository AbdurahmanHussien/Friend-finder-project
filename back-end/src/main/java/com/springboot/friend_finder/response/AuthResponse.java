package com.springboot.friend_finder.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@Builder
public class AuthResponse {

    private Long userId;
    private String token;
    private String refreshToken;
}
