package com.springboot.friend_finder.service.impl;

import com.springboot.friend_finder.dto.UserProfileDto;
import com.springboot.friend_finder.dto.authDto.UserPost;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.entity.auth.UserDetails;
import com.springboot.friend_finder.exceptions.ResourceNotFoundException;
import com.springboot.friend_finder.mapper.UserMapper;
import com.springboot.friend_finder.mapper.UserProfileMapper;
import com.springboot.friend_finder.repository.auth.UserRepository;
import com.springboot.friend_finder.service.IUserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserProfileService implements IUserProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;

    @Value("${file.avatar-dir}")
    private String uploadAvatarDir;

    @Override
    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));
        return userProfileMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(Long userId, UserProfileDto userProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        UserDetails userDetails = user.getUserDetails();
        if (userDetails == null) {
            userDetails = new UserDetails();
            user.setUserDetails(userDetails);
        }

        userDetails.setName(userProfileDto.getName());
        userDetails.setPhoneNum(userProfileDto.getPhoneNum());
        userDetails.setGender(userProfileDto.getGender());

        User updatedUser = userRepository.save(user);
        return userProfileMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public UserProfileDto addAvatar(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = Paths.get(uploadAvatarDir, fileName).toString();

        File dest = new File(filePath);
        dest.getParentFile().mkdirs();
        file.transferTo(dest);

        UserDetails userDetails = user.getUserDetails();
        if (userDetails == null) {
            userDetails = new UserDetails();
            user.setUserDetails(userDetails);
        }

        userDetails.setProfileImage("/avatars/" + fileName);

        User updatedUser = userRepository.save(user);
        return userProfileMapper.toDto(updatedUser);
    }

    @Override
    public UserPost getMyProfile(Long userId) {
        return userMapper.userToUserPost(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user.not.found")));
    }
}
