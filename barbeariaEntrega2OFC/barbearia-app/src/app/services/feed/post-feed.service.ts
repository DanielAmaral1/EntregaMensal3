import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostFeed, ComentarioFeed } from '../../model/feed/post-feed.model';

@Injectable({
  providedIn: 'root'
})
export class PostFeedService {

  private apiUrl = 'http://localhost:9090/api/feed';

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

  curtirPost(idPost: number): Observable<PostFeed> {
    return this.http.post<PostFeed>(`${this.apiUrl}/${idPost}/curtir`, {});
  }

  descurtirPost(idPost: number): Observable<PostFeed> {
    return this.http.post<PostFeed>(`${this.apiUrl}/${idPost}/descurtir`, {});
  }

  adicionarComentario(idPost: number, comentario: ComentarioFeed): Observable<ComentarioFeed> {
    return this.http.post<ComentarioFeed>(`${this.apiUrl}/${idPost}/comentarios`, comentario);
  }

  getComentarios(idPost: number): Observable<ComentarioFeed[]> {
    return this.http.get<ComentarioFeed[]>(`${this.apiUrl}/${idPost}/comentarios`);
  }

  removerComentario(idComentario: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/comentarios/${idComentario}`);
  }

  findByAutor(idCliente: number): Observable<PostFeed[]> {
    return this.http.get<PostFeed[]>(`${this.apiUrl}/autor/${idCliente}`);
  }

  pesquisarPorConteudo(termo: string): Observable<PostFeed[]> {
    return this.http.get<PostFeed[]>(`${this.apiUrl}/pesquisar?termo=${termo}`);
  }

  findByCurtidasMinimas(curtidas: number): Observable<PostFeed[]> {
    return this.http.get<PostFeed[]>(`${this.apiUrl}/curtidas-minimas/${curtidas}`);
  }
}

