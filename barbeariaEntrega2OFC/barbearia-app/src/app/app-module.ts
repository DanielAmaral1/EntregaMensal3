import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app.component';
import { PagInicialComponent } from './components/pag-inicial/pag-inicial.component';
import { ClientesComponent } from './components/clientes/clientes.component';
import { AgendamentosComponent } from './components/agendamentos/agendamentos.component';
import { ServicosComponent } from './components/servicos/servicos.component';
import { FuncionariosComponent } from './components/funcionarios/funcionarios.component';
import { ProdutosComponent } from './components/produtos/produtos.component';

@NgModule({
  declarations: [
    AppComponent,
    PagInicialComponent,
    ClientesComponent,
    AgendamentosComponent,
    ServicosComponent,
    FuncionariosComponent,
    ProdutosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }