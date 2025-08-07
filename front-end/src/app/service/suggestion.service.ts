import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class SuggestionService {

  constructor(private http: HttpClient) { }

  baseUrl: string = 'http://localhost:9090/api/friends/suggestions';

  getSuggestions(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}`);
  }

  sendFriendRequest(userId: number): Observable<any> {
    return this.http.post(`http://localhost:9090/api/friends/request?receiverId=${userId}`, {});
  }


  isFriend(friendId: number): Observable<boolean> {  //GET http://localhost:9090/api/friends/isFriend/{{friendId}}
    return this.http.get<boolean>(`http://localhost:9090/api/friends/isFriend/${friendId}`);
  }

  deleteFriend(friendId: number): Observable<any> {  //http://localhost:9090/api/friends/friend/delete?friendId={{$random.integer(100)}}
    return this.http.delete(`http://localhost:9090/api/friends/friend/delete?friendId=${friendId}`);
  }
}
