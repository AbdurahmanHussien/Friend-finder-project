package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.CommentsReplyDto;
import com.springboot.friend_finder.entity.CommentsReply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses ={ CommentMapper.class, UserMapper.class})
public interface ReplyMapper {


    @Mapping(target = "commentId", source = "comment.id")
    CommentsReplyDto toDto(CommentsReply commentsReply);

    @Mapping(target = "commentId", source = "comment.id")
    List<CommentsReplyDto> toDtoList(List<CommentsReply> commentsReplies);

    @Mapping(target = "comment.id", source = "commentId")
    CommentsReply toEntity(CommentsReplyDto commentsReplyDto);

    @Mapping(target = "comment.id", source = "commentId")
    List<CommentsReply> toEntityList(List<CommentsReplyDto> commentsReplyDtos);
}
