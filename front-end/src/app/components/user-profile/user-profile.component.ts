import {Component, OnInit} from '@angular/core';
import {AsyncPipe, DatePipe} from '@angular/common';
import {Profile} from '../../model/Profile';
import {ProfileService} from '../../service/profile.service';
import {TimelineService} from '../../service/timeline.service';
import {Post} from '../../model/post';
import {PostComponent} from '../post/post.component';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {SidebarService} from '../../service/sidebar.service';
import {SuggestionService} from '../../service/suggestion.service';
import {ToastrService} from 'ngx-toastr';
import {Observable} from 'rxjs';
import {TimelineComponent} from '../timeline/timeline.component';

@Component({
  selector: 'app-user-profile',
  imports: [
    DatePipe,
    FormsModule,
    RouterLink,
    AsyncPipe,
    TimelineComponent,
    PostComponent
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {


  user = JSON.parse(localStorage.getItem("user")!);

  isFriends: boolean = false;


  latestActions: string[] = [
    'Followed Ahmed',
    'Liked a post',
    'Commented on Sara\'s post',
    'Updated profile picture'
  ];

  profile : Profile = {
    id: 0,
    name: '',
    about: '',
    profileImage: '',
    profileCoverImage: '',
    phoneNum: '',
    gender: '',
    birthDate: ''
  };

  constructor(private profileService: ProfileService,
              private timelineService: TimelineService,
              private route: ActivatedRoute,
              private sidebarService: SidebarService,
              private suggestionService: SuggestionService,
              private toastr: ToastrService
  ) { }


  posts: Post[] = [];

  friendsNumber: number = 0

  ngOnInit(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    const userId = this.route.snapshot.params['id'];
    this.profileService.getProfile(userId).subscribe(user => {
      this.profile = user;
    });


    this.timelineService.getPostByUserId(userId).subscribe(posts => {
      this.posts = posts;
    });

    this.sidebarService.friendsNumber$.subscribe(count => {
      this.friendsNumber = count;
    });
    this.sidebarService.updateFriendsNumber();
    this.checkFriendStatus(userId);

    }

  addFriend(userId: number) {
    this.suggestionService.sendFriendRequest(userId).subscribe(() => {
      this.toastr.success('Friend request sent to !'+ this.profile.name, 'Friend Request', {
        timeOut: 5000,
        positionClass: 'toast-bottom-right',
        progressBar: true,
        closeButton: true
      });
    });
  }

  checkFriendStatus(userId: number) {
    this.suggestionService.isFriend(userId).subscribe((res) => {
      this.isFriends = res;
    });
  }

    getProfile(id: number) {
      this.profileService.getProfile(id).subscribe(profile => {
        this.profile = profile;
      });
    }


    getUserPosts(id: number) {
      this.timelineService.getPostByUserId(id).subscribe(posts => {
        this.posts = posts;
      });
    }

  handleDelete(postId: number) {
    this.posts = this.posts.filter(p => p.id !== postId);
  }


  onCoverError( event:Event) {
    (event.target as HTMLImageElement).src = 'assets/images/missing.png';

  }
  onProfileError( event:Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';

  }


  getFriendsNumber(){
    return this.sidebarService.getFriendsNumber().subscribe(res => {
      this.friendsNumber = res;
    });
  }

  getAge(birthDate: string | Date): number {
    const birth = new Date(birthDate);
    const today = new Date();

    let age = today.getFullYear() - birth.getFullYear();
    const m = today.getMonth() - birth.getMonth();

    if (m < 0 || (m === 0 && today.getDate() < birth.getDate())) {
      age--;
    }

    return age;
  }

  removeFriend(userId: number) {
    this.suggestionService.deleteFriend(userId).subscribe(() => {
      this.toastr.error('You unfriended '+ this.profile.name, '', {
        timeOut: 5000,
        positionClass: 'toast-bottom-right',
        progressBar: true,
        closeButton: true
      });
    });
  }
}

