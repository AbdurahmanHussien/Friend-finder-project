package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.auth.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


	List<Post> findByUser(User user);



	@Query("""
			SELECT p FROM Post p WHERE p.user.id IN (
			SELECT CASE
			WHEN f.sender.id = :userId THEN f.receiver.id
			WHEN f.receiver.id = :userId THEN f.sender.id
			END FROM Friendship f
			WHERE (f.sender.id = :userId OR f.receiver.id = :userId) AND f.status = 'ACCEPTED')
			OR p.user.id = :userId
			Order by p.createdAt DESC
			""")
	List<Post> findTimelinePosts(@Param("userId") Long userId);

	@Query("""
	SELECT p FROM Post p WHERE
	p.mediaType = 'image' AND
	(p.user.id IN (
		SELECT CASE
			WHEN f.sender.id = :userId THEN f.receiver.id
			WHEN f.receiver.id = :userId THEN f.sender.id
		END FROM Friendship f
		WHERE (f.sender.id = :userId OR f.receiver.id = :userId) AND f.status = 'ACCEPTED')
		OR p.user.id = :userId)
	ORDER BY p.createdAt DESC
""")
	List<Post> findTimelineImagePosts(@Param("userId") Long userId);

	@Query("""
	SELECT p FROM Post p WHERE
	p.mediaType = 'video' AND
	(p.user.id IN (
		SELECT CASE
			WHEN f.sender.id = :userId THEN f.receiver.id
			WHEN f.receiver.id = :userId THEN f.sender.id
		END FROM Friendship f
		WHERE (f.sender.id = :userId OR f.receiver.id = :userId) AND f.status = 'ACCEPTED')
		OR p.user.id = :userId)
	ORDER BY p.createdAt DESC
""")
	List<Post> findTimelineVideoPosts(@Param("userId") Long userId);

	List<Post> getPostsByUserAndMediaType(User user, String mediaType);
}
