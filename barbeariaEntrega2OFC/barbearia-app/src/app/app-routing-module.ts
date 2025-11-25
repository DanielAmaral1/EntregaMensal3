import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
import { TestFuncionarioComponent } from './test-funcionario.component';
import { AuthGuard } from './core/auth/auth.guard';
import { RoleGuard } from './core/auth/role.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'test-funcionario', component: TestFuncionarioComponent },
  { path: '', component: PagInicialComponent, canActivate: [AuthGuard] },
  { path: 'clientes', component: ClientesComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'MASTER' } },
  { path: 'agendamentos', component: AgendamentosComponent, canActivate: [AuthGuard] },
  { path: 'servicos', component: ServicosComponent, canActivate: [AuthGuard] },
  { path: 'funcionarios', component: FuncionariosComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'MASTER' } },
  { path: 'produtos', component: ProdutosComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'MASTER' } },
  { path: 'avaliacoes', component: AvaliacoesComponent, canActivate: [AuthGuard] },
  { path: 'feed', component: FeedComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
