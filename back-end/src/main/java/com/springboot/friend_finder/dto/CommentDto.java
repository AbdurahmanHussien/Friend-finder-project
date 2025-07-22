package com.springboot.friend_finder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

	private Long id;

	private String content;

	private LocalDateTime createdAt;

	private Integer likeCount;


	private Long postId;


	private Long userId;

	private List<CommentsReplyDto> replies;
}


