import { EstadosCadastroComponent } from './estados-cadastro/estados-cadastro.component';
import { EstadosPesquisaComponent } from './estados-pesquisa/estados-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: EstadosPesquisaComponent },
{  path: 'new',    component: EstadosCadastroComponent },
{  path: ':id/edit', component: EstadosCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstadosRoutingModule { }
