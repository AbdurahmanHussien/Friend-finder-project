package com.springboot.friend_finder.service;

public interface ICommentService {

	void createComment(Long postId, Long userId, String content);

	void updateComment(Long commentId, String newContent);

	void deleteComment(Long commentId);

	void likeComment(Long commentId, Long userId);
}
