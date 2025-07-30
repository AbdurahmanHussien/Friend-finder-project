import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Post} from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class TimelineService {

  constructor(private http: HttpClient) { }

  baseUrl: string = 'http://localhost:9090/api/posts';

  getTimeline(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.baseUrl}/timeline`);
  }

  createPost(content: string, file?: File): Observable<any> {
    const formData = new FormData();
    formData.append('content', content);
    if (file) {
      formData.append('file', file);
    }
    return this.http.post<any>('http://localhost:9090/api/posts', formData);
  }
}
