package com.springboot.friend_finder.controller;

import com.springboot.friend_finder.dto.CommentDto;
import com.springboot.friend_finder.mapper.UserMapper;
import com.springboot.friend_finder.service.ICommentService;
import com.springboot.friend_finder.service.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentDto.setUser(userMapper.userToUserPost(userDetails.getUser()));
        return new ResponseEntity<>(commentService.createComment(commentDto), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long commentId) {
        commentDto.setId(commentId);
        return ResponseEntity.ok(commentService.updateComment(commentDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{commentId}/likeUnlike")
    public ResponseEntity<String> likeUnlikeComment(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentService.likeAndUnlikeComment(commentId, userDetails.getId());
        return ResponseEntity.ok("Done");
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }
}
