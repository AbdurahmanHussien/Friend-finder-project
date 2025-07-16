package com.springboot.friend_finder.repository.auth;

import com.springboot.friend_finder.constant.RoleType;
import com.springboot.friend_finder.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(RoleType roleType);
    boolean existsRoleByRoleType( RoleType roleType);

}
