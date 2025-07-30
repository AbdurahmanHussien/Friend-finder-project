import {Reply} from './Reply';
import {User} from './User';

export interface Comment {
  id?: number;
  createdAt: Date;
  content: string;
  likeCount: number;
  postId?: number;
  user: User;
  replies?: Reply[];
}
