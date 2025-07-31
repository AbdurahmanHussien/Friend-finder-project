import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {HeaderService} from '../../service/header.service';
import {Friendship} from '../../model/Friendsip';

@Component({
  selector: 'app-header',
  imports: [
    RouterLink,
    NgForOf,
    NgIf,
    RouterLinkActive,
    NgClass
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  constructor(private headerService: HeaderService, private router: Router) {

  }
  friends: Friendship[] = [];

  requestsMenuOpen = false;

  toggleRequestsMenu() {
    this.requestsMenuOpen = !this.requestsMenuOpen;
  }
  ngOnInit() {
    this.loadFriendRequests();
  }

  loadFriendRequests() {
    this.headerService.getPendingRequestsForUser().subscribe({
      next: (res) => this.friends = res,
      error: (err) => console.error(err)
    });
  }

  acceptRequest(requestId: number) {
    this.headerService.acceptRequest(requestId).subscribe(() => {
      this.loadFriendRequests();
    });
  }

  rejectRequest(requestId: number) {
    this.headerService.rejectRequest(requestId).subscribe(() => {
      this.loadFriendRequests();
    });
  }

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';
  }

  logout() {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('user');
    localStorage.removeItem('theme')

    this.router.navigate(['/login'],
      {
        queryParams: { loggedOut: 'true' }
      });
  }
}
