import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GruposFinanceirosPesquisaComponent } from './grupos-financeiros-pesquisa/grupos-financeiros-pesquisa.component';
import { GruposFinanceirosCadastroComponent } from './grupos-financeiros-cadastro/grupos-financeiros-cadastro.component';
const routes: Routes = [
   {  path: '',        component: GruposFinanceirosPesquisaComponent },
   {  path: 'new',    component: GruposFinanceirosCadastroComponent },
   {  path: ':id/edit', component: GruposFinanceirosCadastroComponent }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
})

export class GruposFinanceirosRoutingModule { }
