import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Friendship} from '../model/Friendsip';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private http: HttpClient) { }

  baseUrl: string = 'http://localhost:9090/api/friends'
  getPendingRequestsForUser(): Observable<Friendship[]> {
    return this.http.get<any[]>(`${this.baseUrl}/requests/pending`);
  }

  acceptRequest(senderId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/request/accept?senderId=${senderId}`, {});
  }

  rejectRequest(senderId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/request/reject?senderId=${senderId}`, {});
  }

}
