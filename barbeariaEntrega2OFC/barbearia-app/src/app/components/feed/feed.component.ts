import { Component, OnInit } from '@angular/core';
import { PostFeedService } from '../../services/feed/post-feed.service';
import { ClienteService } from '../../services/cliente/cliente.service';
import { PostFeed, ComentarioFeed } from '../../model/feed/post-feed.model';
import { Cliente } from '../../model/clientes/cliente.model';

@Component({
  selector: 'app-feed',
  standalone: false,
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  mostrarFormulario = false;
  editandoPost = false;
  postEditandoId?: number;
  
  post: PostFeed = {
    conteudo: '',
    autor: { id_cliente: 0 }
  };
  
  posts: PostFeed[] = [];
  postsFiltrados: PostFeed[] = [];
  clientes: Cliente[] = [];
  termoPesquisa: string = '';
  
  comentariosExpandidos: Set<number> = new Set();
  novoComentario: { [key: number]: string } = {};
  
  erros = {
    conteudo: '',
    autor: ''
  };

  constructor(
    private postFeedService: PostFeedService,
    private clienteService: ClienteService
  ) {}
  
  ngOnInit() {
    this.carregarPosts();
    this.carregarClientes();
  }

  carregarPosts() {
    this.postFeedService.findAll().subscribe({
      next: (posts) => {
        this.posts = posts;
        this.postsFiltrados = posts;
        posts.forEach(post => {
          if (post.id_post) {
            this.carregarComentarios(post.id_post);
          }
        });
      },
      error: (error) => console.error('Erro ao carregar posts:', error)
    });
  }

  carregarComentarios(idPost: number) {
    this.postFeedService.getComentarios(idPost).subscribe({
      next: (comentarios) => {
        const post = this.posts.find(p => p.id_post === idPost);
        if (post) {
          post.comentarios = comentarios;
        }
      },
      error: (error) => console.error('Erro ao carregar comentários:', error)
    });
  }

  carregarClientes() {
    this.clienteService.findAll().subscribe({
      next: (clientes) => {
        this.clientes = clientes;
      },
      error: (error) => console.error('Erro ao carregar clientes:', error)
    });
  }

  pesquisar() {
    if (!this.termoPesquisa.trim()) {
      this.postsFiltrados = this.posts;
      return;
    }

    const termo = this.termoPesquisa.toLowerCase();
    this.postsFiltrados = this.posts.filter(p => 
      p.conteudo.toLowerCase().includes(termo) ||
      p.autor?.nome?.toLowerCase().includes(termo)
    );
  }

  limparPesquisa() {
    this.termoPesquisa = '';
    this.postsFiltrados = this.posts;
  }

  cadastrarPost() {
    if (this.validarFormulario()) {
      if (this.editandoPost && this.postEditandoId) {
        this.postFeedService.update(this.postEditandoId, this.post).subscribe({
          next: () => {
            alert('Post atualizado com sucesso!');
            this.resetForm();
            this.carregarPosts();
          },
          error: (error) => {
            console.error('Erro ao atualizar post:', error);
            alert('Erro ao atualizar post!');
          }
        });
      } else {
        this.postFeedService.save(this.post).subscribe({
          next: () => {
            alert('Post publicado com sucesso!');
            this.resetForm();
            this.carregarPosts();
          },
          error: (error) => {
            console.error('Erro ao publicar post:', error);
            alert('Erro ao publicar post!');
          }
        });
      }
    }
  }
  
  editarPost(post: PostFeed) {
    this.post = { ...post };
    this.editandoPost = true;
    this.postEditandoId = post.id_post;
    this.mostrarFormulario = true;
  }
  
  deletarPost(post: PostFeed) {
    if (confirm('Tem certeza que deseja deletar este post?') && post.id_post) {
      this.postFeedService.deleteById(post.id_post).subscribe({
        next: () => {
          alert('Post deletado com sucesso!');
          this.carregarPosts();
        },
        error: (error) => {
          console.error('Erro ao deletar post:', error);
          alert('Erro ao deletar post!');
        }
      });
    }
  }

  curtirPost(post: PostFeed) {
    if (post.id_post) {
      this.postFeedService.curtirPost(post.id_post).subscribe({
        next: (updatedPost) => {
          post.curtidas = updatedPost.curtidas;
        },
        error: (error) => {
          console.error('Erro ao curtir post:', error);
          alert('Erro ao curtir post!');
        }
      });
    }
  }

  descurtirPost(post: PostFeed) {
    if (post.id_post && post.curtidas && post.curtidas > 0) {
      this.postFeedService.descurtirPost(post.id_post).subscribe({
        next: (updatedPost) => {
          post.curtidas = updatedPost.curtidas;
        },
        error: (error) => {
          console.error('Erro ao descurtir post:', error);
          alert('Erro ao descurtir post!');
        }
      });
    }
  }

  toggleComentarios(idPost: number) {
    if (this.comentariosExpandidos.has(idPost)) {
      this.comentariosExpandidos.delete(idPost);
    } else {
      this.comentariosExpandidos.add(idPost);
      this.carregarComentarios(idPost);
    }
  }

  adicionarComentario(post: PostFeed) {
    if (!post.id_post || !this.novoComentario[post.id_post]?.trim()) {
      alert('O comentário não pode estar vazio!');
      return;
    }

    // Usa o primeiro cliente disponível como autor do comentário (protótipo)
    // Em produção, deveria usar o cliente logado
    if (this.clientes.length === 0) {
      alert('Nenhum cliente disponível para comentar!');
      return;
    }

    const comentario: ComentarioFeed = {
      texto: this.novoComentario[post.id_post],
      autor: { id_cliente: this.clientes[0].id_cliente }
    };

    this.postFeedService.adicionarComentario(post.id_post, comentario).subscribe({
      next: () => {
        this.novoComentario[post.id_post!] = '';
        this.carregarComentarios(post.id_post);
        alert('Comentário adicionado com sucesso!');
      },
      error: (error) => {
        console.error('Erro ao adicionar comentário:', error);
        alert('Erro ao adicionar comentário!');
      }
    });
  }

  removerComentario(idComentario: number, idPost: number) {
    if (confirm('Tem certeza que deseja remover este comentário?')) {
      this.postFeedService.removerComentario(idComentario).subscribe({
        next: () => {
          this.carregarComentarios(idPost);
        },
        error: (error) => {
          console.error('Erro ao remover comentário:', error);
          alert('Erro ao remover comentário!');
        }
      });
    }
  }
  
  cancelarEdicao() {
    this.resetForm();
  }

  private resetForm() {
    this.editandoPost = false;
    this.postEditandoId = undefined;
    this.post = { conteudo: '', autor: { id_cliente: 0 } };
    this.limparErros();
    this.mostrarFormulario = false;
  }

  private validarFormulario(): boolean {
    this.limparErros();
    let valido = true;
    
    if (!this.validarConteudo()) valido = false;
    if (!this.validarAutor()) valido = false;
    
    return valido;
  }

  private limparErros(): void {
    this.erros = { conteudo: '', autor: '' };
  }

  private validarConteudo(): boolean {
    if (!this.post.conteudo || this.post.conteudo.trim().length < 3) {
      this.erros.conteudo = 'O conteúdo deve ter no mínimo 3 caracteres!';
      return false;
    }
    return true;
  }

  private validarAutor(): boolean {
    if (!this.post.autor || !this.post.autor.id_cliente) {
      this.erros.autor = 'Selecione um autor!';
      return false;
    }
    return true;
  }
}

