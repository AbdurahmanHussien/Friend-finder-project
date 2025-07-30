import {Component, OnInit} from '@angular/core';
import {SuggestionService} from '../../service/suggestion.service';
import {User} from '../../model/User';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-suggestion',
  imports: [
    NgForOf
  ],
  templateUrl: './suggestion.component.html',
  styleUrl: './suggestion.component.css'
})
export class SuggestionComponent implements OnInit {

  constructor(private suggestionService: SuggestionService) { }


  users: User[] = [];


  ngOnInit() {
    this.suggestionService.getSuggestions().subscribe((data) => {
      this.users = data;
    });
  }

  addFriend(userId: number) {
    this.suggestionService.sendFriendRequest(userId).subscribe(() => {
      this.users = this.users.filter(u => u.id !== userId);
    });
  }
  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';
  }

}
