import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {User} from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor( private http: HttpClient) {}


  login(data: any): Observable<any> {
    return this.http.post('http://localhost:9090/api/auth/login', data);
  }

  signup(data: any): Observable<any> {
    return this.http.post('http://localhost:9090/api/auth/signup', data);
  }

  resetPassword(data: any): Observable<any> {
    return this.http.post(`http://localhost:9090/api/auth/reset-password`, data);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('jwt_token');
  }

  setUser(user: User) {
    console.log('user set', user);
    this.currentUserSubject.next(user);
  }

  getUser(): User | null {
    return this.currentUserSubject.value;
  }


  getMyProfile(): Observable<User> {
    return this.http.get<User>(`http://localhost:9090/api/profile/me`);
  }


}
