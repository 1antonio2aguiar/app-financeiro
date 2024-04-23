import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CidadesPesquisaComponent } from './cidades-pesquisa/cidades-pesquisa.component';
import { CidadesCadastroComponent } from './cidades-cadastro/cidades-cadastro.component';

const routes: Routes = [

{  path: '',        component: CidadesPesquisaComponent },
{  path: 'new',    component: CidadesCadastroComponent },
{  path: ':id/edit', component: CidadesCadastroComponent }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
})

export class CidadesRoutingModule { }
