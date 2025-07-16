package com.springboot.friend_finder.entity.auth;

import com.springboot.friend_finder.constant.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend_finder_roles")
@Builder
public class Role implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "role_type")
        @Enumerated(EnumType.STRING)
        private RoleType roleType;



         @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
         private Set<User> users = new HashSet<>();

         public Role(RoleType roleType) {
                this.roleType = roleType;
         }


}
