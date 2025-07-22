package com.springboot.friend_finder.entity.auth;


import com.springboot.friend_finder.entity.Comment;
import com.springboot.friend_finder.entity.CommentsReply;
import com.springboot.friend_finder.entity.Friendship;
import com.springboot.friend_finder.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend_finder_users")
public class User {

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


    @OneToMany(mappedBy = "sender")
    private List<Friendship> sentRequests;

    @OneToMany(mappedBy = "receiver")
    private List<Friendship> receivedRequests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> post;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentsReply> replies;





}
