package com.springboot.friend_finder.entity;

import com.springboot.friend_finder.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CommentsReply {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	private String content;

	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private Comment comment;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}
