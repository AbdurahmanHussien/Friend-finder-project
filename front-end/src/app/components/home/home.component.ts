import { Component, OnInit } from '@angular/core';
import { TimelineComponent } from '../timeline/timeline.component';
import { CreatePostComponent } from '../create-post/create-post.component';
import { SuggestionComponent } from '../suggestion/suggestion.component';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { TimelineService } from '../../service/timeline.service';
import { BehaviorSubject } from 'rxjs';
import { Post } from '../../model/post';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    TimelineComponent,
    CreatePostComponent,
    SuggestionComponent,
    SidebarComponent
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private postsSubject = new BehaviorSubject<Post[]>([]);
  public posts$ = this.postsSubject.asObservable();

  constructor(private timelineService: TimelineService) {}

  ngOnInit(): void {
    this.getTimeline();
  }

  refreshTimeline() {
    this.getTimeline();
  }

  getTimeline(): void {
    this.timelineService.getTimeline().subscribe(posts => {
      this.postsSubject.next(posts);
    });
  }

}
