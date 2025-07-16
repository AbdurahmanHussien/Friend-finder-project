package com.springboot.friend_finder.repository.auth;

import com.springboot.friend_finder.entity.auth.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository  extends JpaRepository<UserDetails, Long> {
}
