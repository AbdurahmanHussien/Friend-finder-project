package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.CommentsReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<CommentsReply, Long> {

	List<CommentsReply> findByCommentIdOrderByCreatedAtDesc(Long postId);
}
