package com.springboot.friend_finder.service;

import com.springboot.friend_finder.dto.CommentsReplyDto;

import java.util.List;

public interface IReplyService {


	List<CommentsReplyDto> getAllReplies();

	CommentsReplyDto createReply(CommentsReplyDto commentsReplyDto);

	CommentsReplyDto updateReply(CommentsReplyDto commentsReplyDto) ;

	void deleteReply(Long id) ;
	List<CommentsReplyDto> getRepliesByComment(Long commentId);
}
