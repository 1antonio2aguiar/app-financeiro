import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PessoasPesquisaComponent } from './pessoas-pesquisa/pessoas-pesquisa.component';
import { PessoasCadastroComponent } from './pessoas-cadastro/pessoas-cadastro.component';

const routes: Routes = [
   {  path: '', component: PessoasPesquisaComponent },
   {  path: 'new', component: PessoasCadastroComponent },
   {  path: ':id/edit', component: PessoasCadastroComponent }
 ];

 @NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
 })

 export class PessoasRoutingModule { }
