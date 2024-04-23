import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TiposGruposFinanceirosCadastroComponent }
   from './tipos-grupos-financeiros-cadastro/tipos-grupos-financeiros-cadastro.component';
import { TiposGruposFinanceirosPesquisaComponent }
   from './tipos-grupos-financeiros-pesquisa/tipos-grupos-financeiros-pesquisa.component';

const routes: Routes = [
   {  path: '',        component: TiposGruposFinanceirosPesquisaComponent },
   {  path: 'new',    component: TiposGruposFinanceirosCadastroComponent },
   {  path: ':id/edit', component: TiposGruposFinanceirosCadastroComponent }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
})

export class TiposGruposFinanceirosRoutingModule { }
