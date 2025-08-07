import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { Post } from '../../model/post';
import { NgClass, NgOptimizedImage } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TimeagoPipe } from '../../service/timeago.pipe';
import { TimelineService } from '../../service/timeline.service';
import {CommentDto} from '../../model/CommentDto';
import {Reply} from '../../model/Reply';

@Component({
  selector: 'app-post',
  imports: [
    FormsModule,
    TimeagoPipe,
    NgClass
],
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
  standalone: true
})
export class PostComponent implements OnInit {
  @Input() post!: Post;

  user: any;
  imageUrl!: string;

  replyTexts: { [commentId: number]: string } = {};

  comment!: CommentDto;

  reply!: Reply;

  showAllComments: { [postId: number]: boolean } = {};
  expandedReplies: { [commentId: number]: boolean } = {};
  @Output() onDelete = new EventEmitter<unknown>();

  toggleComments(postId: number) {
    this.showAllComments[postId] = true;
  }

  toggleReplies(commentId: number) {
    this.expandedReplies[commentId] = true;
  }

  shouldShowReply(commentId: number, index: number): boolean {
    return this.expandedReplies[commentId] || index < 2;
  }

  constructor(private timelineService: TimelineService) {}

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem("user")!);
    this.imageUrl = `http://localhost:9090${this.user.profileImage}`;

    this.comment = {
      likedByCurrentUser: false,
      id: 0,
      content: '',
      createdAt: new Date(),
      user: this.user,
      likeCount: 0,
      replies: []
    };
    this.reply = {
      id: 0,
      content: '',
      user: this.user,
      createdAt: new Date(),
      commentId: 0
    };
    this.getCommentsByPostId(this.post.id);
  }

  onToggleLike(post: Post) {
    if (post.likedByCurrentUser) {
      post.likeCount--;
    } else {
      post.likeCount++;
    }

    post.likedByCurrentUser = !post.likedByCurrentUser;

    this.timelineService.toggleLike(post).subscribe(updatedPost => {
      post.likeCount = updatedPost.likeCount;

  });
  }


  likeComment(comment: CommentDto) {

    comment.likedByCurrentUser = !comment.likedByCurrentUser;
    if (comment.likedByCurrentUser) {
      // @ts-ignore
      comment.likeCount++;
    } else {
      // @ts-ignore
      comment.likeCount--;
    }

    this.timelineService.commentLike(comment.id).subscribe(updatedComment => {
      comment.likedByCurrentUser = updatedComment.likedByCurrentUser;
      comment.likeCount = updatedComment.likesCount;
    });
  }

  onImageError(event: Event | ErrorEvent) {
    const imgElement = event.target as HTMLImageElement;
    if (imgElement) {
      imgElement.src = 'assets/images/unknown.png';
    }
  }

  addComment() {
    // @ts-ignore
    const newComment: CommentDto = {
      content: this.comment.content,
      postId: this.post?.id!,
      user: this.user,
      createdAt: new Date(),
    };

    this.timelineService.addComment(newComment).subscribe((comment) => {
      // @ts-ignore
      this.post?.comments.push(comment);
      this.post.commentCount++;
      this.comment.content = '';
    });
  }

  addReply(commentId: number) {
    const replyContent = this.replyTexts[commentId];
    if (!replyContent || replyContent.trim() === '') return;

    const newReply: Reply = {
      id: 0,
      content: replyContent,
      user: this.user,
      createdAt: new Date(),
      commentId: commentId
    };

    this.timelineService.addReply(newReply).subscribe((reply) => {
      // @ts-ignore
      const targetComment = this.post.comments.find(c => c.id === commentId);
      if (targetComment?.replies) {
        targetComment.replies.push(reply);
      } else if (targetComment) {
        targetComment.replies = [reply];
      }

      this.replyTexts[commentId] = '';
    });
  }

  getCommentsByPostId(postId:number){
    this.timelineService.getCommentsByPostId(postId).subscribe(comments => {
      this.post.comments = comments;
    });
  }

  isImage(url: string): boolean {
    return /\.(jpg|jpeg|png|gif|webp)$/i.test(url);
  }

  isVideo(url: string): boolean {
    return /\.(mp4|mov|ogg)$/i.test(url);
  }

  deletePost(postId: number) {
    this.timelineService.deletePost(postId).subscribe(() => {
      this.onDelete.emit(postId);
    });
  }

  deleteComment(id: number) {
    this.timelineService.deleteComment(id).subscribe(() => {
      this.post.commentCount--;
      this.getCommentsByPostId(this.post.id);
    });
  }
  deleteReply(id: number) {
    this.timelineService.deleteReply(id).subscribe(() => {
      this.getCommentsByPostId(this.post.id);
    });
  }


}
