package com.springboot.friend_finder.mapper;


import com.springboot.friend_finder.dto.CommentDto;
import com.springboot.friend_finder.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReplyMapper.class, UserMapper.class})
public interface CommentMapper {


	@Mapping(target="postId", source="post.id")
	@Mapping(target="replies", source="replies")
	CommentDto	toDto(Comment comment);

	@Mapping(target="post.id", source="postId")
	@Mapping(target="replies", source="replies")
	Comment toEntity(CommentDto commentDto);


	@Mapping(target="post.id", source="postId")
	@Mapping(target="replies", source="replies")
	List<Comment> toEntityList(List<CommentDto> commentDtos);


	@Mapping(target="postId", source="post.id")
	@Mapping(target="replies", source="replies")
	List<CommentDto>toDtoList(List<Comment> comments);
}
