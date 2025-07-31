import {CommentDto} from './CommentDto';
import {User} from './User';
export interface Post {
  id: number;
  content: string;
  likeCount: number;
  commentCount: number;
  createdAt: Date;
  updatedAt?: Date;
  mediaUrl?: string;
  mediaType?: string;
  likedByCurrentUser: boolean;
  user: User;
  comments: CommentDto[];

}
