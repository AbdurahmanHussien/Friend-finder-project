package com.springboot.friend_finder.response;

import com.springboot.friend_finder.dto.authDto.UserPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@Builder
public class AuthResponse {


    private String token;
    private String refreshToken;
    private UserPost user;
}
