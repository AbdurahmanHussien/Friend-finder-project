package com.springboot.friend_finder.service;

import com.springboot.friend_finder.dto.CommentDto;

import java.util.List;

public interface ICommentService {

	CommentDto createComment(CommentDto commentDto);

	CommentDto updateComment(CommentDto commentDto);

	void deleteComment(Long commentId);

	void likeAndUnlikeComment(Long commentId, Long userId);

	List<CommentDto> getCommentsByPost(Long postId, Long userId);

	CommentDto saveComment(CommentDto commentDto);

}
