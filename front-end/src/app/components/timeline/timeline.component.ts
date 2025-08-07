import {
  Component,
   signal, computed
} from '@angular/core';
import { Post } from '../../model/post';
import {BehaviorSubject} from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { PostComponent } from '../post/post.component';
import {TimelineService} from '../../service/timeline.service';
import {TimelineVideoComponent} from '../timeline-video/timeline-video.component';
import {TimelineAlbumComponent} from '../timeline-album/timeline-album.component';
import {ActivatedRoute, UrlSegment} from '@angular/router';

@Component({
  selector: 'app-timeline',
  imports: [
    AsyncPipe,
    PostComponent,
    TimelineVideoComponent,
    TimelineAlbumComponent
  ],
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css'],
  standalone: true
})
export class TimelineComponent {
  constructor(private timelineService: TimelineService, private route: ActivatedRoute) {

  }

  currentView = signal<'posts' | 'videos' | 'album'>('posts');



  postsSubject = new BehaviorSubject<Post[]>([]);
  postsAlbumSubject = new BehaviorSubject<Post[]>([]);
  postsVideoSubject = new BehaviorSubject<Post[]>([]);

  posts$ = computed(() => {
    const view = this.currentView();
    if (view === 'videos') {
      return this.postsVideoSubject.asObservable();
    } else if (view === 'album') {
      return this.postsAlbumSubject.asObservable();
    } else {
      return this.postsSubject.asObservable();
    }
  });



  ngOnInit(): void {
    this.route.url.subscribe((segments: UrlSegment[]) => {
      const lastSegment = segments[segments.length - 1]?.path;

      if (lastSegment === 'videos') {
        this.currentView.set('videos');
      } else if (lastSegment === 'album') {
        this.currentView.set('album');
      } else {
        this.currentView.set('posts');
      }

      this.reloadBasedOnCurrentView();
    });
  }




  loadPosts() {
    this.timelineService.getTimeline().subscribe(posts => {
      this.postsSubject.next(posts);
    });
  }

  loadPostsVideo() {
    this.timelineService.getTimelineVideo().subscribe(posts => {
      this.postsVideoSubject.next(posts);
    });
  }

  loadPostsAlbum() {
    this.timelineService.getTimelineAlbum().subscribe(posts => {
      this.postsAlbumSubject.next(posts);
    });
  }

  confirmDelete(id: number) {
    this.timelineService.deletePost(id).subscribe(() => {
      this.loadPosts();
    });
  }

  reloadBasedOnCurrentView() {
    const view = this.currentView();

    if (view === 'videos') {
      this.loadPostsVideo();
    } else if (view === 'album') {
      this.loadPostsAlbum();
    } else {
      this.loadPosts();
    }
  }

}
