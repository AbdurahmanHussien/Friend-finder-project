package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


	List<Post> findByUser(User user);
}
