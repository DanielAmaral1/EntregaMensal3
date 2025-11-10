import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PagInicialComponent } from './components/pag-inicial/pag-inicial.component';
import { ClientesComponent } from './components/clientes/clientes.component';
import { AgendamentosComponent } from './components/agendamentos/agendamentos.component';
import { ServicosComponent } from './components/servicos/servicos.component';
import { FuncionariosComponent } from './components/funcionarios/funcionarios.component';
import { ProdutosComponent } from './components/produtos/produtos.component';

const routes: Routes = [
  { path: '', component: PagInicialComponent },
  { path: 'clientes', component: ClientesComponent },
  { path: 'agendamentos', component: AgendamentosComponent },
  { path: 'servicos', component: ServicosComponent },
  { path: 'funcionarios', component: FuncionariosComponent },
  { path: 'produtos', component: ProdutosComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
