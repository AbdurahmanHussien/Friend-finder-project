import {Component, OnInit} from '@angular/core';
import {SuggestionService} from '../../service/suggestion.service';
import {User} from '../../model/User';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-suggestion',
  imports: [],
  templateUrl: './suggestion.component.html',
  styleUrl: './suggestion.component.css'
})
export class SuggestionComponent implements OnInit {

  constructor(private suggestionService: SuggestionService, private toastr: ToastrService, private route: ActivatedRoute,
              private router: Router ) { }


  users: User[] = [];


  ngOnInit() {
    this.suggestionService.getSuggestions().subscribe((data) => {
      this.users = data;
    });
  }

  addFriend(userId: number) {
    this.suggestionService.sendFriendRequest(userId).subscribe(() => {
      this.toastr.success('Friend request sent to !'+ this.users.find(u => u.id === userId)?.name, 'Friend Request', {
        timeOut: 5000,
        positionClass: 'toast-bottom-right',
        progressBar: true,
        closeButton: true
      });
      this.users = this.users.filter(u => u.id !== userId);

    });
  }
  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';
  }

  goToProfile(id: number) {
    if(id){
      this.router.navigate(['profile', id]);
    } else {
      console.log('Invalid user ID');
    }

  }

}
