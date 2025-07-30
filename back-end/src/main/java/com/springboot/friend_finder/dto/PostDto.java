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
public class PostDto {


	private Long id;


	private String content;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Integer likeCount;

	private Integer commentCount;

	private String mediaUrl;  // image/video url

	private String mediaType;  //image, video

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private List<CommentDto> comments;


	private UserPost user;

}
