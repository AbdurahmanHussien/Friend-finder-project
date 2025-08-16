import {Component, EventEmitter, Input, Output} from '@angular/core';
import {User} from '../../model/User';
import {SuggestionService} from '../../service/suggestion.service';

@Component({
  selector: 'app-friends',
  imports: [],
  templateUrl: './friends.component.html',
  styleUrl: './friends.component.css'
})
export class FriendsComponent {


  constructor(private suggestionService: SuggestionService) {}


  @Input() friend!: User;
  @Output() friendRemoved = new EventEmitter<number>();

  @Input() profileId!: number;
  @Input() isFriend!: boolean;


  user = JSON.parse(localStorage.getItem("user")!);

  removeFriend() {
    this.suggestionService.deleteFriend(this.friend.id).subscribe(() => {
      this.friendRemoved.emit(this.friend.id);
      this.friend.isFriend = false;
    });
  }

  addFriend() {
    this.suggestionService.sendFriendRequest(this.friend.id).subscribe(() => {
      this.friend.isFriend = true;
    });
  }
}
