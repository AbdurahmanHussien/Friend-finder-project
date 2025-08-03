package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.Comment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    List<Comment> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt DESC")
    List<Comment> findCommentsByPost(@Param("postId") Long postId);
    
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId ORDER BY c.createdAt DESC")
    List<Comment> findCommentsByUser(@Param("userId") Long userId);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.post WHERE c.id = :id")
    Optional<Comment> findByIdWithPost(@Param("id") Long id);

}
