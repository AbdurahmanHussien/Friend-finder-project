import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  imports: [],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent  {

  user = JSON.parse(localStorage.getItem("user")!);
  name = this.user.name
  imageUrl = `http://localhost:9090${this.user.profileImage}`;


}
