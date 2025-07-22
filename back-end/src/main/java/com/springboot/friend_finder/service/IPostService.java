package com.springboot.friend_finder.service;

import com.springboot.friend_finder.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {

	List<PostDto> getAllPosts();

	List<PostDto> getPostsByUser(Long userId);

	PostDto createPost(Long userId, String content, MultipartFile file)  throws IOException;

	PostDto updatePost(Long postId, String newContent, MultipartFile file)  throws IOException ;

	void deletePost(Long postId) ;
}
