package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.authDto.UserDto;
import com.springboot.friend_finder.dto.authDto.UserPost;
import com.springboot.friend_finder.entity.Comment;
import com.springboot.friend_finder.entity.CommentsReply;
import com.springboot.friend_finder.entity.Friendship;
import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.auth.Role;
import com.springboot.friend_finder.entity.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {
		UserDetailsMapper.class
})
public interface UserMapper {

	@Mapping(target = "userDetails", source = "userDetails")
	@Mapping(target = "roles", source = "roles")
	@Mapping(target = "postsId", source = "post")
	@Mapping(target = "commentsId", source = "comments")
	@Mapping(target = "repliesId", source = "replies")
	@Mapping(target = "sentRequestsIds", source = "sentRequests")
	@Mapping(target = "receivedRequestsIds", source = "receivedRequests")
	UserDto toDto(User user);

	@Mapping(source = "id", target = "id")
	@Mapping(source = "userDetails.name", target = "name")
	@Mapping(source = "userDetails.profileImage", target = "profileImage")
	UserPost userToUserPost(User user);

	@Mapping(source = "id", target = "id")
	@Mapping(source = "userDetails.name", target = "name")
	@Mapping(source = "userDetails.profileImage", target = "profileImage")
	List<UserPost> userToUserPostList(List<User> users);

	@Mapping(target = "userDetails", source = "userDetails")
	@Mapping(target = "roles", ignore = true)
	User toEntity(UserDto userDto);

	List<UserDto> toDtoList(List<User> users);

	List<User> toEntityList(List<UserDto> userDtos);

	default List<Long> mapPostsToPostIds(List<Post> posts) {
		if (posts == null) {
			return null;
		}
		return posts.stream().map(Post::getId).collect(Collectors.toList());
	}

	default List<Long> mapCommentsToCommentIds(List<Comment> comments) {
		if (comments == null) {
			return null;
		}
		return comments.stream().map(Comment::getId).collect(Collectors.toList());
	}

	default List<Long> mapRepliesToReplyIds(List<CommentsReply> replies) {
		if (replies == null) {
			return null;
		}
		return replies.stream().map(CommentsReply::getId).collect(Collectors.toList());
	}

	default List<Long> mapFriendshipsToFriendshipIds(List<Friendship> friendships) {
		if (friendships == null) {
			return null;
		}
		return friendships.stream().map(Friendship::getId).collect(Collectors.toList());
	}

	default Set<Long> mapRolesToRoleIds(Set<Role> roles) {
		if (roles == null) {
			return null;
		}
		return roles.stream().map(Role::getId).collect(Collectors.toSet());
	}
}