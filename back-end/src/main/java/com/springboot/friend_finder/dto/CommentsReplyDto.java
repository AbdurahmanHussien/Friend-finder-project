package com.springboot.friend_finder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsReplyDto {


	private Long id;

	private String content;

	private LocalDateTime createdAt;


	private Long commentId;


	private Long userId;
}
