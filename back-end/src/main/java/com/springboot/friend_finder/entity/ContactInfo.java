package com.springboot.friend_finder.entity;

import com.springboot.friend_finder.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "friend_finder_ContactInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String subject;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}
