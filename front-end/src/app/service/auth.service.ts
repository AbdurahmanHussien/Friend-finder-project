import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {User} from '../model/User';
import {Router} from '@angular/router';

import {ToastrService} from 'ngx-toastr';
import {LoginResponse} from '../model/LoginResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private authStatus = new BehaviorSubject<boolean>(this.hasValidToken());
  public authenticationStatus$: Observable<boolean> = this.authStatus.asObservable();


  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor( private http: HttpClient, private router: Router, private toastr: ToastrService) {}


  login(data: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>('http://localhost:9090/api/auth/login', data).pipe(
      tap((res) => {
        if (res && res.token) {
          localStorage.setItem('jwt_token', res.token);
          localStorage.setItem('refresh_token', res.refreshToken);
          localStorage.setItem("user", JSON.stringify(res.user));
          this.toastr.success('You have been logged in successfully', 'Welcome')
          this.authStatus.next(true);
          ;
          this.router.navigate(['/'])
        }
      })
    );
  }



  signup(data: any): Observable<any> {
    return this.http.post('http://localhost:9090/api/auth/signup', data);
  }

  resetPassword(data: any): Observable<any> {
    return this.http.post(`http://localhost:9090/api/auth/reset-password`, data);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
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

  logout(): void {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('user');
    localStorage.removeItem('refresh_token');

    this.authStatus.next(false);

    this.router.navigate(['/login']);
  }


  private hasValidToken(): boolean {
    return !!localStorage.getItem('jwt_token');
  }
}
