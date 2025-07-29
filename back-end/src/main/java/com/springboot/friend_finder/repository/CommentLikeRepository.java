package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.Comment;
import com.springboot.friend_finder.entity.CommentLike;
import com.springboot.friend_finder.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    boolean existsByUserAndComment(User user, Comment comment);
    int countByComment(Comment comment);
    void deleteByUserAndComment(User user, Comment comment);
}
