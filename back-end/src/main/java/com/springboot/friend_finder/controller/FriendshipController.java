package com.springboot.friend_finder.controller;

import com.springboot.friend_finder.dto.FriendshipDto;
import com.springboot.friend_finder.dto.authDto.UserDto;
import com.springboot.friend_finder.service.auth.CustomUserDetails;
import com.springboot.friend_finder.service.impl.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendshipController {

	private final FriendService friendService;



	@PostMapping("/request")
	public ResponseEntity<FriendshipDto> sendRequest( @AuthenticationPrincipal CustomUserDetails sender, @RequestParam Long receiverId) {
		Long senderId = sender.getId();

		FriendshipDto result = friendService.sendFriendRequest(senderId, receiverId);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/request/accept")
	public ResponseEntity<FriendshipDto> acceptRequest(@RequestParam Long senderId, @AuthenticationPrincipal CustomUserDetails receiver) {
		Long receiverId = receiver.getId();

		FriendshipDto result = friendService.acceptFriendRequest(senderId, receiverId);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/request/reject")
	public ResponseEntity<FriendshipDto> rejectRequest(@RequestParam Long senderId, @AuthenticationPrincipal CustomUserDetails receiver) {

		Long receiverId = receiver.getId();
		System.out.println(senderId);
		FriendshipDto result = friendService.rejectFriendRequest(senderId, receiverId);
		return ResponseEntity.ok(result);
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getFriends(@AuthenticationPrincipal CustomUserDetails user) {
		Long userId = user.getId();
		List<UserDto> result = friendService.getFriends(userId);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/requests/pending")
	public ResponseEntity<List<FriendshipDto>> getPendingRequests(@AuthenticationPrincipal CustomUserDetails user) {
		Long userId = user.getId();
		List<FriendshipDto> result = friendService.getPendingRequests(userId);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/requests/sent")
	public ResponseEntity<List<FriendshipDto>> getSentRequests(@AuthenticationPrincipal CustomUserDetails user) {
		Long userId = user.getId();
		List<FriendshipDto> result = friendService.getSentRequests(userId);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/friend/delete")
	public ResponseEntity<Void> removeFriend(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Long friendId) {
		Long userId = user.getId();
		friendService.deleteRequest(userId, friendId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/suggestions")
	public ResponseEntity<List<UserDto>> getSuggestions(@AuthenticationPrincipal CustomUserDetails user) {
		Long userId = user.getId();
		List<UserDto> result = friendService.getSuggestions(userId);
		return ResponseEntity.ok(result);
	}
}
