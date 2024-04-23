import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaginaNaoEncontradaComponent } from './core/pagina-nao-encontrada.component';

const routes: Routes = [

   { path: 'home', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule) },
   { path: '', redirectTo: 'login', pathMatch: 'full' },
   { path: 'login', loadChildren: () => import('./seguranca/seguranca.module').then(m => m.SegurancaModule) },
   { path: 'troca-senha', loadChildren: () => import('./seguranca/seguranca.module').then(m => m.SegurancaModule) },

   {
      path: "bairros",
      loadChildren: () =>
         import("./modules/bairros/bairros.module").then((m) => m.BairrosModule),
   },

   {
      path: "ceps",
      loadChildren: () =>
         import("./modules/ceps/ceps.module").then((m) => m.CepsModule),
   },

   {
      path: "cidades",
      loadChildren: () =>
         import("./modules/cidades/cidades.module").then((m) => m.CidadesModule),
   },

   {
      path: "estados",
      loadChildren: () =>
      import("./modules/estados/estados.module").then((m) => m.EstadosModule),
   },

   {
      path: "logradouros",
      loadChildren: () =>
         import("./modules/logradouros/logradouros.module").then((m) => m.LogradourosModule),
   },

   {
      path: "pessoas",
      loadChildren: () =>
         import("./modules/pessoas/pessoas.module").then((m) => m.PessoasModule),
   },

   {
      path: "tipos-logradouros",
      loadChildren: () =>
         import("./modules/tipos-logradouros/tipos-logradouros.module").then((m) => m.TiposLogradourosModule),
   },

   {
      path: "grupos-financeiros",
      loadChildren: () =>
         import("./modules/grupos-financeiros/grupos-financeiros.module").then((m) =>
         m.GruposFinanceirosModule),
   },

   {
      path: "tipos-grupos-financeiros",
      loadChildren: () =>
         import("./modules/tipos-grupos-financeiros/tipos-grupos-financeiros.module").then((m) =>
         m.TiposGruposFinanceirosModule),
   },

   {
      path: "contratos",
      loadChildren: () =>
         import("./modules/contratos/contratos.module").then((m) => m.ContratosModule),
   },

   { path: 'pagina-nao-encontrada', component: PaginaNaoEncontradaComponent },
   { path: '**', redirectTo: 'pagina-nao-encontrada' },


];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})

export class AppRoutingModule { }
