package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.Like;
import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByUserAndPost(User user, Post post);
	boolean existsByUserAndPost(User user, Post post);
	int countByPost(Post post);
	void deleteByUserAndPost(User user, Post post);
}