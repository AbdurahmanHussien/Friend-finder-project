package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.PostDto;
import com.springboot.friend_finder.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

	@Mapping(target = "userId", source = "user.id")
	PostDto toDto(Post post);

	@Mapping(target = "user.id", source = "userId")
	Post toEntity(PostDto postDto);

	@Mapping(target = "userId", source = "user.id")
	List<PostDto> toDtoList(List<Post> posts);

	List<Post> toEntityList(List<PostDto> postDtos);
}
