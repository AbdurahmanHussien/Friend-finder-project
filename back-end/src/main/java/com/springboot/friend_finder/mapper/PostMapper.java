package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.PostDto;
import com.springboot.friend_finder.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "comments", source = "comments")
	PostDto toDto(Post post);

	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "comments", source = "comments")
	Post toEntity(PostDto postDto);

	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "comments", source = "comments")
	List<PostDto> toDtoList(List<Post> posts);

	List<Post> toEntityList(List<PostDto> postDtos);
}
