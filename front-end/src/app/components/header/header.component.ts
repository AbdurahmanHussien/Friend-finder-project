import {Component, OnInit, computed, effect, inject, signal, AfterViewInit} from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { HeaderService } from '../../service/header.service';
import { NotificationService } from '../../service/notification.service';
import { Friendship } from '../../model/Friendsip';

import { ToastrService } from 'ngx-toastr';
import {AuthService} from '../../service/auth.service';
import {FriendRequestNotification} from '../../model/FriendRequestNotification';
import {SidebarService} from '../../service/sidebar.service';
import tippy from 'tippy.js';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  imports: [
    RouterLinkActive,
    RouterLink
],
  standalone: true
})
export class HeaderComponent implements OnInit, AfterViewInit {

  // --- Injections ---
  private headerService = inject(HeaderService);
  private notificationService = inject(NotificationService);
  private authService = inject(AuthService);
  private toastr = inject(ToastrService);
  private sidebarService = inject(SidebarService);

  ngAfterViewInit(): void {
    tippy('[data-tippy-content]', {
      theme: 'light',
      animation: 'shift-toward-subtle',
      placement: 'bottom'
    });
  }


  user: any;


  friendsRequest= signal<Friendship[]>([]);
  requestsMenuOpen = signal(false);

  timelineMenuOpen = signal(false);

  friendRequestCount = computed(() => this.friendsRequest().length);

  notifications = this.notificationService.notifications;

  private notificationSound = new Audio('../../assets/sound/notif.wav');

  constructor() {


    effect(() => {
      const allNotifications = this.notifications();
      if (allNotifications.length > 0) {
        const latestNotification = allNotifications[allNotifications.length - 1];

        this.showNotificationToast(latestNotification);

        this.friendsRequest.update(currentFriends =>
          [...currentFriends, latestNotification.friendship]
        );

     this.notificationService.clearNotification(latestNotification.friendship.id);
      }
    });
  }

  ngOnInit(): void {
   this.user = JSON.parse(localStorage.getItem("user")!);

    this.loadFriendRequests();
    this.notificationSound.load();
  }


  private showNotificationToast(notification: FriendRequestNotification) {
    this.notificationSound.play().catch(err => console.error("Error playing sound:", err));

    this.toastr.info(notification.message, 'Friend Request', {
      timeOut: 5000,
      positionClass: 'toast-bottom-right',
      progressBar: true,
      closeButton: true,
    });
  }

  toggleRequestsMenu() {
    this.requestsMenuOpen.update(open => !open);
  }

  toggleTimelineMenu() {
    this.timelineMenuOpen.update(open => !open);
  }

  loadFriendRequests() {
    this.headerService.getPendingRequestsForUser().subscribe({
      next: res => this.friendsRequest.set(res),
      error: err => console.error("Failed to load friend requests:", err)
    });
  }

  acceptRequest(requestId: number) {
    this.headerService.acceptRequest(requestId).subscribe(() => {
      this.friendsRequest.update(friends => friends.filter(f => f.id !== requestId));
      this.loadFriendRequests();
      this.sidebarService.updateFriendsNumber(this.user.id);
      this.toastr.success('Friend request accepted!', 'Friend Request', {
        timeOut: 5000,
        positionClass: 'toast-bottom-right',
        progressBar: true,
        closeButton: true
      });
    });
  }

  rejectRequest(requestId: number) {
    this.headerService.rejectRequest(requestId).subscribe(() => {
      this.friendsRequest.update(friends => friends.filter(f => f.id !== requestId));
     this.loadFriendRequests();
      this.toastr.error('you rejected the friend request' , 'Friend Request',{
        timeOut: 5000,
        positionClass: 'toast-bottom-right',
        progressBar: true,
        closeButton: true
      })
    });
  }

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';
  }

  logout() {
    this.authService.logout();
  }


}
