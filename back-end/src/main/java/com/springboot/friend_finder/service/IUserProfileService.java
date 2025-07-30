package com.springboot.friend_finder.service;

import com.springboot.friend_finder.dto.UserProfileDto;
import com.springboot.friend_finder.dto.authDto.UserPost;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserProfileService {


	UserProfileDto getUserProfile(Long userId);

	UserProfileDto updateUserProfile(Long userId, UserProfileDto userProfileDto);

	UserProfileDto addAvatar(Long userId, MultipartFile file) throws IOException;


	UserPost getMyProfile(Long userId);



}
