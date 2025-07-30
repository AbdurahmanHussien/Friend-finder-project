import {Component, OnInit} from '@angular/core';
import {TimelineService} from '../../service/timeline.service';
import {Post} from '../../model/post';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-timeline',
  imports: [
    DatePipe,
    NgIf,
    NgForOf
  ],
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements  OnInit {


  post: Post []= [];
  constructor(private timelineService: TimelineService) { }
    ngOnInit(): void {
        this.getTimeline();
    }



    getTimeline(){
      this.timelineService.getTimeline().subscribe({
        next: (response) => {
          this.post= response;
          console.log(response);
        },
        error: (error) => {
          console.log(error);
        }
      })
    }

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';
  }

}
