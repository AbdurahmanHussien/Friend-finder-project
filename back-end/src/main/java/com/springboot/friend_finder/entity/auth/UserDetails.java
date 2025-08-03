package com.springboot.friend_finder.entity.auth;

import com.springboot.friend_finder.constant.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "friend_finder_user_details")
public class UserDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String profileImage;

    private String profileCoverImage;

    private String about;

    @Column(name = "birth_date")
    private LocalDate birthDate;


    @OneToOne(mappedBy = "userDetails")
    private User user;



}
