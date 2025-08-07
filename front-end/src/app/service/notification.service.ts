import { Injectable, signal, WritableSignal, computed, effect } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { FriendRequestNotification } from '../model/FriendRequestNotification';

import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private client: Client | null = null;
  private isConnected = signal(false);

  private notificationsSignal: WritableSignal<FriendRequestNotification[]> = signal([]);

  public notifications = this.notificationsSignal.asReadonly();
  public notificationCount = computed(() => this.notifications().length);

  constructor(private authService: AuthService) {

    this.authService.authenticationStatus$.subscribe(isAuthenticated => {
      if (isAuthenticated) {
        setTimeout(() => {
          if (!this.client?.active) {
            this.connect();
          }
        }, 100);
      } else if (this.client?.active) {
        this.disconnect();
      }
    });

    effect(() => {
      console.log(`You have ${this.notificationCount()} notifications.`);
    });
  }

  private connect() {
    const token = this.authService.getToken();
    if (!token) {
      console.error('Cannot connect to WebSocket: JWT Token not found.');
      return;
    }

    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:9090/ws'),

      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      debug: (str) => { console.log(new Date(), str); },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.client.onConnect = (frame) => {
      this.isConnected.set(true);
      console.log('✅ STOMP: Connected to WebSocket:', frame);

      this.client!.subscribe('/user/queue/friend-requests', (message: IMessage) => {
        const newNotification: FriendRequestNotification = JSON.parse(message.body);
        console.log('📬 Received friend request notification:', newNotification);

        this.notificationsSignal.update(currentNotifications =>
          [...currentNotifications, newNotification]
        );
      });

    };

    this.client.onStompError = (frame) => {
      console.error('❌ STOMP: Broker reported error:', frame.headers['message']);
      console.error('Broker details:', frame.body);
      this.isConnected.set(false);
    };

    this.client.onWebSocketClose = () => {
      this.isConnected.set(false);
      console.log('🔌 STOMP: WebSocket connection closed.');
    }

    this.client.activate();
  }

  public disconnect() {
    if (this.client?.active) {
      this.client.deactivate();
      this.isConnected.set(false);
      console.log('STOMP: Disconnected.');
    }
  }

  public clearNotification(notificationId: number) {
    this.notificationsSignal.update(notifications =>
      notifications.filter(n => n.friendship.id !== notificationId)
    );
  }

  public clearAllNotifications() {
    this.notificationsSignal.set([]);
  }
}
