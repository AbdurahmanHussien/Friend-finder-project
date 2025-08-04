import {
  Component,
  Input,
  Output,
  EventEmitter, ViewChild
} from '@angular/core';
import { Post } from '../../model/post';
import {BehaviorSubject, Observable} from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { PostComponent } from '../post/post.component';
import {TimelineService} from '../../service/timeline.service';

@Component({
  selector: 'app-timeline',
  imports: [
    AsyncPipe,
    PostComponent
],
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css'],
  standalone: true
})
export class TimelineComponent {
  constructor(private timelineService: TimelineService) {
  }



  postsSubject = new BehaviorSubject<Post[]>([]);

  get posts$(): Observable<Post[]> {
    return this.postsSubject.asObservable();
  }

  ngOnInit(): void {
    this.loadPosts();
  }


  loadPosts() {
    this.timelineService.getTimeline().subscribe(posts => {
      this.postsSubject.next(posts);
    });
  }

  confirmDelete(id: number) {
    this.timelineService.deletePost(id).subscribe(() => {
      this.loadPosts();
    });
  }
}
