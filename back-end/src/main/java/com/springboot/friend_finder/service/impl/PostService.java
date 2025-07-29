package com.springboot.friend_finder.service.impl;

import com.springboot.friend_finder.dto.PostDto;
import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.PostLike;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.exceptions.ResourceNotFoundException;
import com.springboot.friend_finder.mapper.PostMapper;
import com.springboot.friend_finder.repository.PostLikeRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostMapper postMapper;

	private final PostLikeRepository postLikeRepository;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public List<PostDto> getAllPosts() {
		return postMapper.toDtoList(postRepository.findAll());
	}


	@Override
	public List<PostDto> getPostsByUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user.not.found"));
		List<Post> posts = postRepository.findByUser(user);

		if (posts.isEmpty()) {
			throw new ResourceNotFoundException("user.has.no.posts");
		}
		return postMapper.toDtoList(posts);
	}

	@Transactional
	@Override
	public PostDto createPost(Long userId, String content, MultipartFile file) throws IOException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

		Post post = Post.builder()
				.user(user)
				.content(content)
				.likeCount(0)
				.commentCount(0)
				.build();

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



	@Override
	public List<PostDto> getTimelinePosts(Long userId) {

		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException("user.not.found");
		}
		List<Post> posts = postRepository.findTimelinePosts(userId);
		if (posts.isEmpty()) {
			throw new ResourceNotFoundException("timeline.is.empty");
		}
		return postMapper.toDtoList(postRepository.findTimelinePosts(userId));
	}

	@Override
	@Transactional
	public void likeAndUnlikePost(Long postId, Long userId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post.not.found"));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

		Optional<PostLike> existingLike = postLikeRepository.findByUserAndPost(user, post);

		if (existingLike.isPresent()) {
			// Unlike: remove the like and decrement count
			postLikeRepository.delete(existingLike.get());
			int newLikeCount = post.getLikeCount() == null || post.getLikeCount() <= 0 
				? 0 
				: post.getLikeCount() - 1;
			post.setLikeCount(newLikeCount);
		} else {
			// Like: add new like and increment count
			PostLike newLike = PostLike.builder()
				.post(post)
				.user(user)
				.build();
			postLikeRepository.save(newLike);
			post.setLikeCount(post.getLikeCount() == null ? 1 : post.getLikeCount() + 1);
		}
		
		postRepository.save(post);
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
