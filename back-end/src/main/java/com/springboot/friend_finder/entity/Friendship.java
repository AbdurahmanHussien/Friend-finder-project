package com.springboot.friend_finder.entity;

import com.springboot.friend_finder.constant.RequestStatus;
import com.springboot.friend_finder.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;


	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	private LocalDateTime sentAt;

	private LocalDateTime respondedAt;

}
