package com.springboot.friend_finder.service.impl;

import com.springboot.friend_finder.dto.CommentDto;
import com.springboot.friend_finder.entity.Comment;
import com.springboot.friend_finder.entity.CommentLike;
import com.springboot.friend_finder.entity.Post;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.exceptions.ResourceNotFoundException;
import com.springboot.friend_finder.mapper.CommentMapper;
import com.springboot.friend_finder.repository.CommentLikeRepository;
import com.springboot.friend_finder.repository.CommentRepository;
import com.springboot.friend_finder.repository.PostRepository;
import com.springboot.friend_finder.repository.auth.UserRepository;
import com.springboot.friend_finder.service.ICommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {

        if(commentDto.getId()!=null) {
            throw new ResourceNotFoundException("id.notempty");
        }

        return saveComment(commentDto);

    }



	@Override
	public CommentDto updateComment(CommentDto commentDto) {

		if(commentDto.getId()==null) {
			throw new ResourceNotFoundException("id.empty");
		}
		return saveComment(commentDto);
	}

	@Override
	public void deleteComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment.not.found"));
		
		if (comment.getUser() != null) {
			comment.getUser().getComments().remove(comment);
		}
		
		if (comment.getPost() != null) {
			comment.getPost().getComments().remove(comment);
		}
		
		commentRepository.delete(comment);
	}

	@Override
	@Transactional
	public void likeAndUnlikeComment(Long commentId, Long userId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment.not.found"));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

		Optional<CommentLike> existingLike = commentLikeRepository.findByUserAndComment(user, comment);

		if (existingLike.isPresent()) {
			// Unlike: remove the like and decrement count
			commentLikeRepository.delete(existingLike.get());
			int newLikeCount = comment.getLikeCount() == null || comment.getLikeCount() <= 0 
				? 0 
				: comment.getLikeCount() - 1;
			comment.setLikeCount(newLikeCount);
		} else {
			// Like: add new like and increment count
			CommentLike like = CommentLike.builder()
				.user(user)
				.comment(comment)
				.build();
			commentLikeRepository.save(like);
			comment.setLikeCount(comment.getLikeCount() == null ? 1 : comment.getLikeCount() + 1);
		}
		
		commentRepository.save(comment);
	}
	@Override
    public List<CommentDto> getCommentsByPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("post.not.found");
        }
        
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }


    public CommentDto saveComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("post.not.found"));
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));
                
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setPost(post);
        comment.setUser(user);
        
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }
}
