import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app.component';
import { PagInicialComponent } from './components/pag-inicial/pag-inicial.component';
import { ClientesComponent } from './components/clientes/clientes.component';
import { AgendamentosComponent } from './components/agendamentos/agendamentos.component';
import { ServicosComponent } from './components/servicos/servicos.component';
import { FuncionariosComponent } from './components/funcionarios/funcionarios.component';
import { ProdutosComponent } from './components/produtos/produtos.component';
import { AvaliacoesComponent } from './components/avaliacoes/avaliacoes.component';
import { FeedComponent } from './components/feed/feed.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AuthInterceptor } from './core/auth/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    PagInicialComponent,
    ClientesComponent,
    AgendamentosComponent,
    ServicosComponent,
    FuncionariosComponent,
    ProdutosComponent,
    AvaliacoesComponent,
    FeedComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }