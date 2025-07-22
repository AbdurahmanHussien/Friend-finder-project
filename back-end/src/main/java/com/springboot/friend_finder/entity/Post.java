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
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String content;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Integer likeCount;


	private Integer commentCount;


	private String mediaUrl;  // image/video url

	private String mediaType;  //image, video


	@ManyToOne(fetch = FetchType.LAZY)
	private User user;


}
