import {Component, OnInit, signal} from '@angular/core';
import {DatePipe} from '@angular/common';
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
import {TimelineAlbumComponent} from '../timeline-album/timeline-album.component';
import {TimelineVideoComponent} from '../timeline-video/timeline-video.component';
import {User} from '../../model/User';
import {FriendsComponent} from '../friends/friends.component';



@Component({
  selector: 'app-user-profile',
  imports: [
    DatePipe,
    FormsModule,
    RouterLink,
    PostComponent,
    TimelineAlbumComponent,
    TimelineVideoComponent,
    FriendsComponent
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {


  user = JSON.parse(localStorage.getItem("user")!);



  currentView = signal<'timeline' | 'videos' | 'album' | 'friends'>('timeline');

  posts = signal<Post[]>([]);
  friends = signal<User[]>([]);



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


  friendsNumber: number = 0

  NO_friends= signal<number>(this.friendsNumber);

  areFriends:boolean = false
  isFriends = signal<boolean>(this.areFriends);

  ngOnInit(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    const userId = this.route.snapshot.params['id'];

    // check if query param = friends
    this.route.queryParams.subscribe(params => {
      if (params['view'] === 'friends') {
        this.currentView.set('friends');
        this.loadUserFriends(userId);
      }
    });

    this.profileService.getProfile(userId).subscribe(user => {
      this.profile = user;
    });


    this.timelineService.getPostByUserId(userId).subscribe(posts => {
      this.posts.set(posts);
    });

    this.sidebarService.friendsNumber$.subscribe(count => {
      this.friendsNumber = count;
      this.NO_friends.set(this.friendsNumber);
    });
    this.sidebarService.updateFriendsNumber(userId);
    this.checkFriendStatus(userId);

    }



  setView(view: 'timeline' | 'videos' | 'album' | 'friends') {
    this.currentView.set(view);

    const userId = this.route.snapshot.params['id'];

    if (view === 'timeline') this.loadUserPosts(userId);
    if (view === 'videos') this.loadUserVideos(userId);
    if (view === 'album') this.loadUserAlbums(userId);
    if (view === 'friends') this.loadUserFriends(userId);
  }

  addFriend(userId: number) {
    this.suggestionService.sendFriendRequest(userId).subscribe(() => {
      this.toastr.success('Friend request sent to !'+ this.profile.name, 'Friend Request', {
        timeOut: 5000,
        positionClass: 'toast-bottom-right',
        progressBar: true,
        closeButton: true
      });
      this.isFriends.set(true);
    });
  }

  checkFriendStatus(userId: number) {
    this.suggestionService.isFriend(userId).subscribe((res) => {
      this.areFriends = res;
      this.isFriends.set(res);
    });
  }

    getProfile(id: number) {
      this.profileService.getProfile(id).subscribe(profile => {
        this.profile = profile;
      });
    }


    getUserPosts(id: number) {
      this.timelineService.getPostByUserId(id).subscribe(posts => {
        this.posts.set(posts);
      });
    }

  handleDelete(postId: number) {
    // @ts-ignore
    this.posts = this.posts.filter(post => post.id !== postId);
  }


  onCoverError( event:Event) {
    (event.target as HTMLImageElement).src = 'assets/images/missing.png';

  }
  onProfileError( event:Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';

  }


  getFriendsNumber(userId: number) {
    return this.sidebarService.getFriendsNumber(userId).subscribe(res => {
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
      this.isFriends.set(false);
      this.NO_friends.set(this.NO_friends() - 1);
    });
  }

  loadUserPosts(userId: number) {
    this.timelineService.getPostByUserId(userId).subscribe(res => this.posts.set(res));
  }

  loadUserVideos(userId: number) {
    this.timelineService.getUserVideos(userId).subscribe(res => this.posts.set(res));
  }

  loadUserAlbums(userId: number) {
    this.timelineService.getUserAlbums(userId).subscribe(res => this.posts.set(res));
  }

  loadUserFriends(userId: number) {
    this.sidebarService.getFriendsByUserId(userId).subscribe(friendsList => {
      // @ts-ignore
      friendsList.forEach(friend => {
        this.suggestionService.isFriend(friend.id).subscribe(isFr => {
          friend.isFriend = isFr;
        });
      });

      this.friends.set(friendsList);
    });
  }

}

