package com.springboot.friend_finder.controller;

import com.springboot.friend_finder.dto.PostDto;
import com.springboot.friend_finder.service.auth.CustomUserDetails;
import com.springboot.friend_finder.service.impl.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<PostDto> createPost(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(required = false) String content,
			@RequestParam(required = false) MultipartFile file) throws IOException {

		Long userId = userDetails.getId();
		PostDto post = postService.createPost(userId, content, file);
		return ResponseEntity.ok(post);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(
			@PathVariable Long postId,
			@RequestParam(required = false) String content,
			@RequestParam(required = false) MultipartFile file) throws IOException {

		PostDto updatedPost = postService.updatePost(postId, content, file);
		return ResponseEntity.ok(updatedPost);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
		postService.deletePost(postId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(postService.getPostsByUser(userId));
	}

	@GetMapping("/timeline")
	public ResponseEntity<List<PostDto>> getTimelinePosts(@AuthenticationPrincipal CustomUserDetails userDetails) {

		Long userId = userDetails.getId();
		return ResponseEntity.ok(postService.getTimelinePosts(userId));
	}

	@PostMapping("/{postId}/like")
	public ResponseEntity<String> likePost(@PathVariable Long postId,
	                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

		Long userId = userDetails.getId();
		postService.likePost(postId, userId);
		return ResponseEntity.ok("Post liked successfully");
	}

	@PostMapping("/{postId}/dislike")
	public ResponseEntity<String> unLikePost(@PathVariable Long postId,
	                                       @AuthenticationPrincipal CustomUserDetails userDetails) {

		Long userId = userDetails.getId();
		postService.unlikePost(postId, userId);
		return ResponseEntity.ok("Post disliked successfully");
	}
}

