package com.springboot.friend_finder.controller;

import com.springboot.friend_finder.dto.UserProfileDto;
import com.springboot.friend_finder.service.IUserProfileService;
import com.springboot.friend_finder.service.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final IUserProfileService userProfileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getUserProfile(userId));
    }

    @PutMapping
    public ResponseEntity<UserProfileDto> updateUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(userDetails.getId(), userProfileDto));
    }

    @PostMapping("/avatar")
    public ResponseEntity<UserProfileDto> addAvatar(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("avatar") MultipartFile file) throws IOException {
        return ResponseEntity.ok(userProfileService.addAvatar(userDetails.getId(), file));
    }
}
