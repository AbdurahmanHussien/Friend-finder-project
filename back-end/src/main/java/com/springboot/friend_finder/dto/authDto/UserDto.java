package com.springboot.friend_finder.dto.authDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String email;

    private String password;

    private UserDetailsDto userDetails;

    private Set<Long> roles;

    private List<Long> postsId;

    private List<Long> commentsId;

    private List<Long> repliesId;

    private List<Long> sentRequestsIds;

    private List<Long> receivedRequestsIds;







}
