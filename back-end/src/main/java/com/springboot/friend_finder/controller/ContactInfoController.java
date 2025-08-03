package com.springboot.friend_finder.controller;


import com.springboot.friend_finder.dto.ContactInfoDto;
import com.springboot.friend_finder.service.auth.CustomUserDetails;
import com.springboot.friend_finder.service.impl.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactInfoController {

        private final ContactService contactService;



    @PostMapping
    @Operation(summary = "add  contact Info")
    public ResponseEntity<ContactInfoDto> addContactInfo(@Valid @RequestBody ContactInfoDto contactInfoDto, @AuthenticationPrincipal UserDetails user ) {
            Long userId = ((CustomUserDetails) user).getId();
            contactInfoDto.setUserId(userId);
            return ResponseEntity.ok(contactService.addInfo(contactInfoDto));
    }

    @GetMapping
    @Operation(summary = "get all contact Info")
    public ResponseEntity<List<ContactInfoDto>> getAllContactInfo() {
            return ResponseEntity.ok(contactService.getAllInfo());

    }
    @GetMapping("/user")
    @Operation(summary = "get all contact Info for user")
    public ResponseEntity<List<ContactInfoDto>> getAllContactInfoByUserId(@AuthenticationPrincipal UserDetails user) {
            Long userId = ((CustomUserDetails) user).getId();
            return ResponseEntity.ok(contactService.getAllInfoByUserId(userId));

    }

}
