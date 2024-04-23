import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ContratosPesquisaComponent } from './contratos-pesquisa/contratos-pesquisa.component';
import { ContratosCadastroComponent } from './contratos-cadastro/contratos-cadastro.component';

const routes: Routes = [
   {  path: '', component: ContratosPesquisaComponent },
   {  path: 'new', component: ContratosCadastroComponent },
   {  path: ':id/edit', component: ContratosCadastroComponent }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
})

export class ContratosRoutingModule { }
