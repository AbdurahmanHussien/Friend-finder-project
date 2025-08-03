package com.springboot.friend_finder.dto.authDto;

import com.springboot.friend_finder.constant.Gender;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {


    private Long id;

    private String name;

    private String phoneNum;

    private Gender gender;

    private String profileImage;

    private String profileCoverImage;

    private String about;

    @Column(name = "birth_date")
    private LocalDate birthDate;


}
