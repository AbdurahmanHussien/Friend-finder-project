import {User} from './User';

export interface Reply {
  id: number;
  content: string;
  createdAt: Date;
  updatedAt?: Date;
  commentId: number;
  user: User;

}
