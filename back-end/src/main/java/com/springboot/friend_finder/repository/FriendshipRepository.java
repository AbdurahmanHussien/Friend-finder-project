package com.springboot.friend_finder.repository;

import com.springboot.friend_finder.entity.Friendship;
import com.springboot.friend_finder.entity.auth.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

	@Query("""
    SELECT fr.receiver FROM Friendship fr 
    WHERE fr.sender.id = :userId AND fr.status = 'ACCEPTED' 
    UNION 
    SELECT fr.sender FROM Friendship fr 
    WHERE fr.receiver.id = :userId AND fr.status = 'ACCEPTED'
""")
	List<User> findFriendsOfUser(@Param("userId") Long userId);

	List<Friendship> findAllBySender(User user);

	List<Friendship> findAllByReceiver(User user);


	@Query("SELECT fr FROM Friendship fr WHERE fr.receiver = :user AND fr.status = 'PENDING'")
	List<Friendship> findPendingRequestsForUser(@Param("user") User user);


	void deleteFriendshipById(Long id);


	@Query("""
			SELECT fr FROM Friendship fr
						WHERE fr.sender = :sender AND fr.receiver = :receiver
			 OR fr.sender = :receiver AND fr.receiver = :sender""")
	Optional<Friendship> findBySenderAndReceiver(User sender, User receiver);


	@Query("""
    SELECT fr FROM Friendship fr
    WHERE (fr.sender = :user1 AND fr.receiver = :user2)
       OR (fr.sender = :user2 AND fr.receiver = :user1)
""")
	Optional<Friendship> findExistingFriendshipBetween(@Param("user1") User user1, @Param("user2") User user2);



	@Query("""
    SELECT u FROM User u
    WHERE u.id != :userId AND u.id NOT IN (
        SELECT f.sender.id FROM Friendship f WHERE f.receiver.id = :userId
        UNION
        SELECT f.receiver.id FROM Friendship f WHERE f.sender.id = :userId
    )
""")
	List<User> findSuggestedUsers(@Param("userId") Long userId, Pageable pageable);

    @Query("""
    SELECT COUNT(fr) FROM Friendship fr 
    WHERE (fr.sender.id = :userId OR fr.receiver.id = :userId) 
    AND fr.status = 'ACCEPTED'
    """)
    int countFriendsOfUser(@Param("userId") Long userId);



	@Query("""
  SELECT COUNT(fr) > 0 FROM Friendship fr
  WHERE (fr.sender = :sender AND fr.receiver = :receiver AND fr.status = 'ACCEPTED')
     OR (fr.sender = :receiver AND fr.receiver = :sender AND fr.status = 'ACCEPTED')
""")
	boolean existsFriendshipBySenderAndReceiverAndStatus(@Param("sender") User sender, @Param("receiver") User receiver);

}
