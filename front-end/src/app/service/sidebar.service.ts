import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  constructor(private http: HttpClient) { }


  getFriendsNumber(): Observable<any> {
    return this.http.get<any[]>(` http://localhost:9090/api/friends/count`);
  }

  //http://localhost:9090/api/profile/avatar

  addAvatar(file: File): Observable<any> {
     const formData = new FormData();
    formData.append('avatar', file);
    return this.http.post<any>('http://localhost:9090/api/profile/avatar', formData);
  }
}
