package com.springboot.friend_finder.dto.authDto;

import com.springboot.friend_finder.constant.Gender;
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

    private String email;

    private String phoneNum;

    private Gender gender;

    private String profileImage;


}
