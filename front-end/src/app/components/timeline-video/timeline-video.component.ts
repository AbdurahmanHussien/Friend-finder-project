import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Post} from '../../model/post';
import {TimelineService} from '../../service/timeline.service';
import {TimeagoPipe} from '../../service/timeago.pipe';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-timeline-video',
  imports: [
    TimeagoPipe,
    NgClass
  ],
  templateUrl: './timeline-video.component.html',
  styleUrl: './timeline-video.component.css'
})
export class TimelineVideoComponent {

  @Output() postDeleted = new EventEmitter<unknown>();

  @Input() post!: Post;


  user: any;

  constructor(private timelineService: TimelineService) {}

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem("user")!);
  }


  deletePost(id: number) {
    this.timelineService.deletePost(id).subscribe(() => {
      this.postDeleted.emit(id);
    });
  }

  onImageError(event: Event | ErrorEvent) {
    const imgElement = event.target as HTMLImageElement;
    if (imgElement) {
      imgElement.src = 'assets/images/unknown.png';
    }
  }

  onToggleLike(post: Post) {
    if (post.likedByCurrentUser) {
      post.likeCount--;
    } else {
      post.likeCount++;
    }

    post.likedByCurrentUser = !post.likedByCurrentUser;

    this.timelineService.toggleLike(post).subscribe(updatedPost => {
      post.likeCount = updatedPost.likeCount;

    });
  }
}
