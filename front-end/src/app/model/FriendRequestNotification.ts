import {Friendship} from './Friendsip';

export interface FriendRequestNotification {
  senderName: string;
  message: string;
  friendship: Friendship;
}
