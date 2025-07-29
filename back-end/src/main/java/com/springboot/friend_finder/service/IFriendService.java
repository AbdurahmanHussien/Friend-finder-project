package com.springboot.friend_finder.service;

import com.springboot.friend_finder.dto.FriendshipDto;
import com.springboot.friend_finder.dto.authDto.UserDto;

import java.util.List;

public interface IFriendService {

	List<UserDto> getFriends(Long userId);

	FriendshipDto acceptFriendRequest(Long senderId, Long receiverId);

	FriendshipDto sendFriendRequest(Long senderId, Long receiverId);

	FriendshipDto rejectFriendRequest(Long senderId, Long receiverId);

	List<FriendshipDto> getPendingRequests(Long userId);

	List<FriendshipDto> getSentRequests(Long userId);

	void deleteRequest(Long senderId, Long receiverId);

	List<UserDto> getSuggestions(Long userId);


}
