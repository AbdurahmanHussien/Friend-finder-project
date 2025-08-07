package com.springboot.friend_finder.controller;

import com.springboot.friend_finder.dto.FriendshipDto;
import com.springboot.friend_finder.dto.authDto.UserDto;
import com.springboot.friend_finder.dto.authDto.UserPost;
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

		FriendshipDto result = friendService.sendFriendRequest(sender.getId(), receiverId);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/request/accept")
	public ResponseEntity<FriendshipDto> acceptRequest(@RequestParam Long senderId, @AuthenticationPrincipal CustomUserDetails receiver) {

		FriendshipDto result = friendService.acceptFriendRequest(senderId, receiver.getId());
		return ResponseEntity.ok(result);
	}

	@PostMapping("/request/reject")
	public ResponseEntity<FriendshipDto> rejectRequest(@RequestParam Long senderId, @AuthenticationPrincipal CustomUserDetails receiver) {

		FriendshipDto result = friendService.rejectFriendRequest(senderId, receiver.getId());
		return ResponseEntity.ok(result);
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getFriends(@AuthenticationPrincipal CustomUserDetails user) {
		List<UserDto> result = friendService.getFriends(user.getId());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/requests/pending")
	public ResponseEntity<List<FriendshipDto>> getPendingRequests(@AuthenticationPrincipal CustomUserDetails user) {
		List<FriendshipDto> result = friendService.getPendingRequests(user.getId());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/requests/sent")
	public ResponseEntity<List<FriendshipDto>> getSentRequests(@AuthenticationPrincipal CustomUserDetails user) {
		List<FriendshipDto> result = friendService.getSentRequests(user.getId());
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/friend/delete")
	public ResponseEntity<Void> removeFriend(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Long friendId) {
		friendService.deleteRequest(user.getId(), friendId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/suggestions")
	public ResponseEntity<List<UserPost>> getSuggestions(@AuthenticationPrincipal CustomUserDetails user) {
		List<UserPost> result = friendService.getSuggestions(user.getId());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> getFriendCount(@AuthenticationPrincipal CustomUserDetails user) {
		int count = friendService.getFriendCount(user.getId());
		return ResponseEntity.ok(count);
	}

	@GetMapping("/isFriend/{friendId}")
	public ResponseEntity<Boolean> isFriend(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long friendId) {
		return ResponseEntity.ok(friendService.isFriend(user.getId(), friendId));
	}
}
