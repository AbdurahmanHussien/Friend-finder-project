package com.springboot.friend_finder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.friend_finder.dto.authDto.UserPost;
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


	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime createdAt;

	private Integer likeCount;


	private Long postId;


	private UserPost user;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private List<CommentsReplyDto> replies;
}


