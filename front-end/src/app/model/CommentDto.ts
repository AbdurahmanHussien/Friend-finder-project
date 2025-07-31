import {Reply} from './Reply';
import {User} from './User';

export interface CommentDto {
  id: number;
  createdAt: Date;
  content: string;
  likeCount?: number;
  postId?: number;
  user: User;
  likedByCurrentUser:boolean
  replies?: Reply[];

  replying?: boolean;
  replyContent?: string;
}
