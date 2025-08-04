import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  constructor(private http: HttpClient) { }


  private friendsNumberSubject = new BehaviorSubject<number>(0);
  friendsNumber$ = this.friendsNumberSubject.asObservable();

  getFriendsNumber(): Observable<any> {
    return this.http.get<number>(` http://localhost:9090/api/friends/count`);
  }

  updateFriendsNumber() {
    this.getFriendsNumber().subscribe(count => {
      this.friendsNumberSubject.next(count);
    });
  }

  //http://localhost:9090/api/profile/avatar

  addAvatar(file: File): Observable<any> {
     const formData = new FormData();
    formData.append('avatar', file);
    return this.http.post<any>('http://localhost:9090/api/profile/avatar', formData);
  }
}
