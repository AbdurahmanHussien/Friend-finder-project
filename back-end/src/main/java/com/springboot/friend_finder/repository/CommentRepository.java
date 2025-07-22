package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
