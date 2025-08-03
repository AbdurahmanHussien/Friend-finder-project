package com.springboot.friend_finder.service.impl;

import com.springboot.friend_finder.dto.CommentsReplyDto;
import com.springboot.friend_finder.entity.Comment;
import com.springboot.friend_finder.entity.CommentsReply;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.exceptions.ResourceNotFoundException;
import com.springboot.friend_finder.mapper.ReplyMapper;
import com.springboot.friend_finder.repository.CommentReplyRepository;
import com.springboot.friend_finder.repository.CommentRepository;
import com.springboot.friend_finder.repository.auth.UserRepository;
import com.springboot.friend_finder.service.IReplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService implements IReplyService {

    private final CommentReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReplyMapper replyMapper;

    @Override
    public List<CommentsReplyDto> getAllReplies() {
        return replyRepository.findAll().stream()
                .map(replyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentsReplyDto createReply(CommentsReplyDto commentsReplyDto) {
        if (commentsReplyDto.getId() != null) {
            throw new ResourceNotFoundException("id.notempty");
        }
        return saveReply(commentsReplyDto);
    }

    @Override
    @Transactional
    public CommentsReplyDto updateReply(CommentsReplyDto commentsReplyDto) {
        if (commentsReplyDto.getId() == null) {
            throw new ResourceNotFoundException("id.empty");
        }
        return saveReply(commentsReplyDto);
    }

    @Override
    @Transactional
    public void deleteReply(Long id) {
        CommentsReply reply = replyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("reply.not.found"));
        replyRepository.delete(reply);
    }

    @Override
    public List<CommentsReplyDto> getRepliesByComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("comment.not.found");
        }
        List<CommentsReply> replies = replyRepository.findByCommentIdOrderByCreatedAtDesc(commentId);

        return replies.stream()
                .map(replyMapper::toDto)
                .collect(Collectors.toList());
    }

    private CommentsReplyDto saveReply(CommentsReplyDto commentsReplyDto) {
        Comment comment = commentRepository.findById(commentsReplyDto.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("comment.not.found"));
        User user = userRepository.findById(commentsReplyDto.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        CommentsReply reply = replyMapper.toEntity(commentsReplyDto);
        reply.setComment(comment);
        reply.setUser(user);

        CommentsReply savedReply = replyRepository.save(reply);
        return replyMapper.toDto(savedReply);
    }
}
