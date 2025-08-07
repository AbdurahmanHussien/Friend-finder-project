package com.springboot.friend_finder.dto;


import com.springboot.friend_finder.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

	private Long id;

	private String name;

	private String phoneNum;

	private Gender gender;

	private String profileImage;

	private String profileCoverImage;

	private String about;

	private LocalDate birthDate;

	private List<Long> postsId;
}
