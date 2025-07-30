import { Component, ViewChild } from '@angular/core';
import { TimelineComponent } from '../timeline/timeline.component';
import { PostComponent } from '../post/post.component';
import { SuggestionComponent } from '../suggestion/suggestion.component';
import {SidebarComponent} from '../sidebar/sidebar.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    TimelineComponent,
    PostComponent,
    SuggestionComponent,
    SidebarComponent
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  @ViewChild(TimelineComponent) timelineComponent!: TimelineComponent;

  refreshTimeline() {
    if (this.timelineComponent) {
      this.timelineComponent.getTimeline();
    }
  }
}
