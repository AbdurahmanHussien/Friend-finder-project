import {Component, OnInit} from '@angular/core';
import {DatePipe} from '@angular/common';
import {Profile} from '../../model/Profile';
import {ProfileService} from '../../service/profile.service';
import {TimelineService} from '../../service/timeline.service';
import {Post} from '../../model/post';
import {PostComponent} from '../post/post.component';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {SidebarService} from '../../service/sidebar.service';

@Component({
  selector: 'app-user-profile',
  imports: [
    DatePipe,
    PostComponent,
    FormsModule,
    RouterLink
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {


  user = JSON.parse(localStorage.getItem("user")!);


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
              private sidebarService: SidebarService
  ) { }


  posts: Post[] = [];

  friendsNumber: number = 0

  ngOnInit(): void {

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


  onImageError( event:Event) {
    (event.target as HTMLImageElement).src = 'assets/images/missing.png';

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

}

