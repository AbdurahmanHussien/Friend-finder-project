import { Component, OnInit } from '@angular/core';
import { TimelineComponent } from '../timeline/timeline.component';
import { CreatePostComponent } from '../create-post/create-post.component';
import { SuggestionComponent } from '../suggestion/suggestion.component';
import { SidebarComponent } from '../sidebar/sidebar.component';


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
export class HomeComponent {


}
