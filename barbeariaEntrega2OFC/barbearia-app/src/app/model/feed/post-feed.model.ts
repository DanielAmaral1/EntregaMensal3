export interface PostFeed {
  id_post?: number;
  conteudo: string;
  dataPost?: string;
  curtidas?: number;
  autor?: {
    id_cliente: number;
    nome?: string;
  };
  comentarios?: ComentarioFeed[];
}

export interface ComentarioFeed {
  id_comentario?: number;
  texto: string;
  dataComentario?: string;
  autor?: {
    id_cliente: number;
    nome?: string;
  };
  post?: {
    id_post: number;
  };
}

