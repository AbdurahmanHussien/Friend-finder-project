package com.springboot.friend_finder.dto;

import com.springboot.friend_finder.constant.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendshipDto {

	private Long id;

	private Long senderId;

	private Long receiverId;

	private RequestStatus status;

	private LocalDateTime sentAt;

	private LocalDateTime respondedAt;
}
