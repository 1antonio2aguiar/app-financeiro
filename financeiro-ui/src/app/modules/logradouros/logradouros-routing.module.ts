import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LogradourosCadastroComponent } from './logradouros-cadastro/logradouros-cadastro.component';
import { LogradourosPesquisaComponent } from './logradouros-pesquisa/logradouros-pesquisa.component';

const routes: Routes = [
   {  path: '',        component: LogradourosPesquisaComponent },
   {  path: 'new',    component: LogradourosCadastroComponent },
   {  path: ':id/edit', component: LogradourosCadastroComponent }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
})
export class LogradourosRoutingModule { }
