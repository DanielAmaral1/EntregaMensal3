import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostFeed, ComentarioFeed } from '../../model/feed/post-feed.model';

@Injectable({
  providedIn: 'root'
})
export class PostFeedService {

  private apiUrl = 'http://localhost:8086/api/feed';

  constructor(private http: HttpClient) { }

  save(post: PostFeed): Observable<PostFeed> {
    return this.http.post<PostFeed>(this.apiUrl, post);
  }

  findAll(): Observable<PostFeed[]> {
    return this.http.get<PostFeed[]>(this.apiUrl);
  }

  findById(id: number): Observable<PostFeed> {
    return this.http.get<PostFeed>(`${this.apiUrl}/${id}`);
  }

  update(id: number, post: PostFeed): Observable<PostFeed> {
    return this.http.put<PostFeed>(`${this.apiUrl}/${id}`, post);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }



  findByAutor(idCliente: number): Observable<PostFeed[]> {
    return this.http.get<PostFeed[]>(`${this.apiUrl}/autor/${idCliente}`);
  }

  pesquisarPorConteudo(termo: string): Observable<PostFeed[]> {
    return this.http.get<PostFeed[]>(`${this.apiUrl}/pesquisar?termo=${termo}`);
  }

  curtirPost(id: number): Observable<PostFeed> {
    return this.http.post<PostFeed>(`${this.apiUrl}/${id}/curtir`, {});
  }

}

