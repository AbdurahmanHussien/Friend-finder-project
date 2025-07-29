package com.springboot.friend_finder.controller;

import com.springboot.friend_finder.dto.CommentsReplyDto;
import com.springboot.friend_finder.service.IReplyService;
import com.springboot.friend_finder.service.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final IReplyService replyService;

    @PostMapping
    public ResponseEntity<CommentsReplyDto> createReply(@RequestBody CommentsReplyDto commentsReplyDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentsReplyDto.setUserId(userDetails.getId());
        return new ResponseEntity<>(replyService.createReply(commentsReplyDto), HttpStatus.CREATED);
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<CommentsReplyDto> updateReply(@RequestBody CommentsReplyDto commentsReplyDto, @PathVariable Long replyId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentsReplyDto.setId(replyId);
        commentsReplyDto.setUserId(userDetails.getId());
        return ResponseEntity.ok(replyService.updateReply(commentsReplyDto));
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<CommentsReplyDto>> getRepliesByComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(replyService.getRepliesByComment(commentId));
    }

    @GetMapping
    public ResponseEntity<List<CommentsReplyDto>> getAllReplies() {
        return ResponseEntity.ok(replyService.getAllReplies());
    }
}
