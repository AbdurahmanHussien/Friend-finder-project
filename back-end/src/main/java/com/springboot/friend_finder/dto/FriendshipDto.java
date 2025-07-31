package com.springboot.friend_finder.dto;

import com.springboot.friend_finder.constant.RequestStatus;
import com.springboot.friend_finder.dto.authDto.UserPost;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendshipDto {

	private Long id;

	private UserPost sender;

	private Long receiverId;

	private RequestStatus status;

	private LocalDateTime sentAt;

	private LocalDateTime respondedAt;
}
