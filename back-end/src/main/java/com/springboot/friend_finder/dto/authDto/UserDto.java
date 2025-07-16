package com.springboot.friend_finder.dto.authDto;


import com.springboot.friend_finder.entity.auth.Role;
import com.springboot.friend_finder.entity.auth.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    
    private String username;

    private String password;

    private UserDetails userDetails;

    List<Role> roles ;







}
