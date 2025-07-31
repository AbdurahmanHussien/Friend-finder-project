import {User} from './User';

export interface Friendship {
  id: number;
  sender: User;
  receiverId: number;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  sentAt: string;
  respondedAt: string | null;
}
