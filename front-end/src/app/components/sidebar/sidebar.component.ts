import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SidebarService} from '../../service/sidebar.service';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-sidebar',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {

  constructor(private sidebarService: SidebarService) { }

  @ViewChild('fileInput') fileInput: ElementRef | undefined;

  triggerFileInput() {
    this.fileInput?.nativeElement.click();
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.sidebarService.addAvatar(file).subscribe(res => {
        this.imageUrl = `http://localhost:9090${res}`;
      });
    }
  }

  ngOnInit(): void {
    this.sidebarService.friendsNumber$.subscribe(count => {
      this.friendsNumber = count;
    });
    this.sidebarService.updateFriendsNumber();
  }

  friendsNumber: number = 0

  user = JSON.parse(localStorage.getItem("user")!);
  name = this.user.name
  imageUrl = `http://localhost:9090${this.user.profileImage}`;

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';
  }

  getFriendsNumber(){
    return this.sidebarService.getFriendsNumber().subscribe(res => {
      this.friendsNumber = res;
    });
  }

}
