package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.UserProfileDto;
import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.entity.auth.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mappings({
        @Mapping(target = "id", source = "user.id"),
        @Mapping(target = "name", source = "user.userDetails.name"),
        @Mapping(target = "phoneNum", source = "user.userDetails.phoneNum"),
        @Mapping(target = "gender", source = "user.userDetails.gender"),
        @Mapping(target = "profileImage", source = "user.userDetails.profileImage"),
        @Mapping(target = "postsId", source = "user.post")
    })
    UserProfileDto toDto(User user);

    default List<Long> mapPostsToPostIds(List<Post> posts) {
        if (posts == null) {
            return null;
        }
        return posts.stream().map(Post::getId).collect(Collectors.toList());
    }
}
