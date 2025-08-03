package com.springboot.friend_finder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final SimpMessagingTemplate messagingTemplate;


	public void sendNotificationToUser(String email, Object payload) {
		messagingTemplate.convertAndSendToUser(email, "/queue/friend-requests", payload);
	}

}
