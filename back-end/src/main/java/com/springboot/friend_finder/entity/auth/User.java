package com.springboot.friend_finder.entity.auth;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend_finder_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;


    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
     @JoinTable(
             name = "friend_finder_user_roles",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id")
     )
    private Set<Role> roles = new HashSet<>();




}
