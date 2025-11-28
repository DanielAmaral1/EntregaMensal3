import { Component, OnInit } from '@angular/core';
import { PostFeedService } from '../../services/feed/post-feed.service';
import { ClienteService } from '../../services/cliente/cliente.service';
import { AuthService } from '../../core/auth/auth.service';
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
    autor: { id_cliente: 0, nome: '' },
    curtidas: 0
  };
  
  posts: PostFeed[] = [];
  postsFiltrados: PostFeed[] = [];
  clientes: Cliente[] = [];
  termoPesquisa: string = '';
  

  
  erros = {
    conteudo: '',
    autor: ''
  };

  constructor(
    private postFeedService: PostFeedService,
    private clienteService: ClienteService,
    private authService: AuthService
  ) {}
  
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
  
  isCliente(): boolean {
    return this.authService.isCliente();
  }
  
  getClienteId(): number | null {
    return this.authService.getClienteId();
  }
  
  ngOnInit() {
    this.carregarPosts();
    this.carregarClientes();
    this.configurarAutorLogado();
  }

  configurarAutorLogado() {
    if (this.authService.isCliente()) {
      const clienteId = this.authService.getClienteId();
      const nomeCliente = this.authService.getUsername();
      if (clienteId && nomeCliente) {
        this.post.autor = { id_cliente: clienteId, nome: nomeCliente };
      }
    } else if (this.authService.isAdmin()) {
      // Buscar ou criar cliente Route 48
      this.buscarClienteRoute48();
    }
  }

  buscarClienteRoute48() {
    this.clienteService.findAll().subscribe({
      next: (clientes) => {
        let route48 = clientes.find(c => c.nome === 'Route 48');
        if (route48) {
          this.post.autor = { id_cliente: route48.id_cliente || 0, nome: 'Route 48' };
        } else {
          // Se não existir, criar cliente Route 48
          this.criarClienteRoute48();
        }
      },
      error: () => {
        this.post.autor = { id_cliente: 1, nome: 'Route 48' }; // Fallback
      }
    });
  }

  criarClienteRoute48() {
    const clienteRoute48 = {
      nome: 'Route 48',
      celular: '(00) 00000-0000',
      email: 'route48@barbearia.com',
      password: 'admin123'
    };
    
    this.clienteService.save(clienteRoute48).subscribe({
      next: (cliente) => {
        this.post.autor = { id_cliente: cliente.id_cliente || 0, nome: 'Route 48' };
      },
      error: () => {
        this.post.autor = { id_cliente: 1, nome: 'Route 48' }; // Fallback
      }
    });
  }

  carregarPosts() {
    this.postFeedService.findAll().subscribe({
      next: (posts) => {
        console.log('Posts carregados:', posts);
        // Garantir que todos os posts tenham curtidas inicializadas
        this.posts = posts.map(p => ({
          ...p,
          curtidas: p.curtidas ?? 0
        }));
        // Ordenar por data (mais recentes primeiro)
        this.posts.sort((a, b) => {
          if (!a.dataPost && !b.dataPost) return 0;
          if (!a.dataPost) return 1;
          if (!b.dataPost) return -1;
          return new Date(b.dataPost).getTime() - new Date(a.dataPost).getTime();
        });
        this.postsFiltrados = [...this.posts];
      },
      error: (error) => {
        console.error('Erro ao carregar posts:', error);
        console.error('Status:', error.status);
        console.error('Message:', error.message);
      }
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
      this.postsFiltrados = [...this.posts];
      return;
    }

    const termo = this.termoPesquisa.toLowerCase();
    this.postsFiltrados = this.posts.filter(p => 
      p.conteudo?.toLowerCase().includes(termo) ||
      p.autor?.nome?.toLowerCase().includes(termo) ||
      p.autorNome?.toLowerCase().includes(termo)
    );
  }

  limparPesquisa() {
    this.termoPesquisa = '';
    this.postsFiltrados = [...this.posts];
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
          next: (response) => {
            console.log('Post salvo:', response);
            alert('Post publicado com sucesso!');
            this.resetForm();
            // Aguarda um pouco antes de recarregar
            setTimeout(() => {
              this.carregarPosts();
            }, 500);
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


  
  cancelarEdicao() {
    this.resetForm();
  }

  curtirPost(post: PostFeed) {
    if (post.id_post) {
      this.postFeedService.curtirPost(post.id_post).subscribe({
        next: (postAtualizado) => {
          // Garantir que curtidas está inicializado
          if (postAtualizado.curtidas === null || postAtualizado.curtidas === undefined) {
            postAtualizado.curtidas = 0;
          }
          // Atualiza o post na lista
          const index = this.posts.findIndex(p => p.id_post === post.id_post);
          if (index !== -1) {
            this.posts[index] = postAtualizado;
            // Atualiza também em postsFiltrados se o post estiver lá
            const indexFiltrado = this.postsFiltrados.findIndex(p => p.id_post === post.id_post);
            if (indexFiltrado !== -1) {
              this.postsFiltrados[indexFiltrado] = postAtualizado;
            } else {
              this.postsFiltrados = [...this.posts];
            }
          }
        },
        error: (error) => {
          console.error('Erro ao curtir post:', error);
          alert('Erro ao curtir post!');
        }
      });
    }
  }

  private resetForm() {
    this.editandoPost = false;
    this.postEditandoId = undefined;
    this.post = { conteudo: '', autor: { id_cliente: 0, nome: '' }, curtidas: 0 };
    this.configurarAutorLogado();
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
    return true;
  }
}

