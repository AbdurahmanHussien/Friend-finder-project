import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Post} from '../model/post';
import {CommentDto} from '../model/CommentDto';
import {Reply} from '../model/Reply';

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
  toggleLike(post: Post): Observable<any> {
    return this.http.post<any>(`http://localhost:9090/api/posts/${post.id}/likeUnlike`, {});
  }  //POST http://localhost:9090/api/posts/{{postId}}/likeUnlike

  commentLike(commentId: number): Observable<any> {
    return this.http.post<any>(`http://localhost:9090/api/comment/${commentId}/likeUnlike`, {});
  }
  getPostById(postId: number): Observable<Post> {
    return this.http.get<Post>(`http://localhost:9090/api/posts/${postId}`);
  }

  addComment(comment: CommentDto): Observable<any> {
    return this.http.post<any>(`http://localhost:9090/api/comment`, comment);
  }

  addReply(reply: Reply): Observable<any> {
    return this.http.post<any>(`http://localhost:9090/api/reply`, reply);
  }

  getCommentsByPostId(postId: number): Observable<CommentDto[]> {
    return this.http.get<CommentDto[]>(`http://localhost:9090/api/comment/post/${postId}`);
  //http://localhost:9090/api/comment/post/{{postId}}
  }

  deletePost(postId: number): Observable<any> {
    return this.http.delete<any>(`http://localhost:9090/api/posts/${postId}`);
  }
  deleteComment(commentId: number): Observable<any> {
    return this.http.delete<any>(`http://localhost:9090/api/comment/${commentId}`);   //http://localhost:9090/api/comment/{{commentId}}
  }

  deleteReply(replyId: number): Observable<any> {
    return this.http.delete<any>(`http://localhost:9090/api/reply/${replyId}`);   //http://localhost:9090/api/reply/{{replyId}}
  }
}
