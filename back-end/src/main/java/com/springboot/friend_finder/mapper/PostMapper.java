package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.PostDto;
import com.springboot.friend_finder.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { CommentMapper.class, UserMapper.class })
public interface PostMapper {


	@Mapping(target = "comments", source = "comments")
	PostDto toDto(Post post);

	@Mapping(target = "comments", source = "comments")
	Post toEntity(PostDto postDto);

	@Mapping(target = "comments", source = "comments")
	List<PostDto> toDtoList(List<Post> posts);

	List<Post> toEntityList(List<PostDto> postDtos);
}
