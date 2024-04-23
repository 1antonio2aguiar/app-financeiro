import { TiposLogradourosCadastroComponent } from './tipos-logradouros-cadastro/tipos-logradouros-cadastro.component';
import { TiposLogradourosPesquisaComponent } from './tipos-logradouros-pesquisa/tipos-logradouros-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposLogradourosPesquisaComponent },
   {  path: 'new',    component: TiposLogradourosCadastroComponent },
   {  path: ':id/edit', component: TiposLogradourosCadastroComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposLogradourosRoutingModule { }
