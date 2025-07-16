package com.springboot.friend_finder.dto.authDto;

import com.springboot.friend_finder.entity.auth.User;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {


    private Long id;

    private String name;

    private String phoneNum;

    @Email
    private String email;

    private String address;

    private User user;



}
