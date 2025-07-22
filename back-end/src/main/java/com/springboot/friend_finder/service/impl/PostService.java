package com.springboot.friend_finder.service.impl;

import com.springboot.friend_finder.dto.PostDto;
import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.exceptions.ResourceNotFoundException;
import com.springboot.friend_finder.mapper.PostMapper;
import com.springboot.friend_finder.repository.PostRepository;
import com.springboot.friend_finder.repository.auth.UserRepository;
import com.springboot.friend_finder.service.IPostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostMapper postMapper;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public List<PostDto> getAllPosts() {
		return postMapper.toDtoList(postRepository.findAll());
	}


	@Override
	public List<PostDto> getPostsByUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		return postMapper.toDtoList(postRepository.findByUser(user));
	}

	@Transactional
	@Override
	public PostDto createPost(Long userId, String content, MultipartFile file) throws IOException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

		Post post = new Post();
		post.setUser(user);
		post.setContent(content);
		post.setCreatedAt(LocalDateTime.now());
		post.setUpdatedAt(LocalDateTime.now());
		post.setLikeCount(0);
		post.setCommentCount(0);

		if (file != null && !file.isEmpty()) {
			String originalName = file.getOriginalFilename();
			String extension = originalName.substring(originalName.lastIndexOf("."));
			String uniqueName = UUID.randomUUID().toString() + extension;
			String fullPath = uploadDir + File.separator + uniqueName;

			// make sure the directory exists
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			file.transferTo(new File(fullPath));

			if (extension.equalsIgnoreCase(".mp4") || extension.equalsIgnoreCase(".mov")) {
				post.setMediaType("video");
				post.setMediaUrl(uniqueName);
			} else {
				post.setMediaType("image");
				post.setMediaUrl(uniqueName);
			}
		}

		return postMapper.toDto(postRepository.save(post));
	}

	@Transactional
	@Override
	public PostDto updatePost(Long postId, String newContent, MultipartFile file) throws IOException {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post.not.found"));

		post.setContent(newContent);
		post.setUpdatedAt(LocalDateTime.now());

		if (file != null && !file.isEmpty()) {
			// delete the old file
			deleteFile(post.getMediaUrl());

			String fileName = saveFile(file);
			String extension = getExtension(fileName);

			if (isVideo(extension)) {
				post.setMediaType("video");
			} else {
				post.setMediaType("image");
			}

			post.setMediaUrl(fileName);
		}

		return postMapper.toDto(postRepository.save(post));
	}

	@Transactional
	@Override
	public void deletePost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post.not.found"));

		deleteFile(post.getMediaUrl());

		postRepository.delete(post);
	}








// ========================helper methods ========================

	private String saveFile(MultipartFile file) throws IOException {
		String originalName = file.getOriginalFilename();
		String extension = originalName.substring(originalName.lastIndexOf("."));
		String uniqueName = UUID.randomUUID().toString() + extension;
		String fullPath = uploadDir + File.separator + uniqueName;

		File dir = new File(uploadDir);
		if (!dir.exists()) dir.mkdirs();

		file.transferTo(new File(fullPath));
		return uniqueName;
	}

	private void deleteFile(String fileName) {
		if (fileName == null) return;

		File file = new File(uploadDir + File.separator + fileName);
		if (file.exists()) file.delete();
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	private boolean isVideo(String extension) {
		return extension.equalsIgnoreCase(".mp4") || extension.equalsIgnoreCase(".mov");
	}
}
