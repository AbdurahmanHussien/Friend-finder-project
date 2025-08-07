import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }


  getProfile(userId: number): Observable<any> {
    return this.http.get(`http://localhost:9090/api/profile/${userId}`);
  }
}
