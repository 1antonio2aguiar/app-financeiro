import { Component } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { HttpParams } from '@angular/common/http';

import { TiposLogradouros } from './../../../shared/models/tipos-logradouros';
import { TiposLogradourosFiltro } from '../tipos-logradouros-filtro';
import { TiposLogradourosService } from '../tipos-logradouros.service';

@Component({
  selector: 'app-tipos-logradouros-pesquisa',
  templateUrl: './tipos-logradouros-pesquisa.component.html',
  styleUrls: ['./tipos-logradouros-pesquisa.component.css']
})

export class TiposLogradourosPesquisaComponent extends BaseResourceListComponent<TiposLogradouros> {
   filtro = new TiposLogradourosFiltro();
   resources = [];
   loading = true;
   constructor(
     private tiposLogradourosService: TiposLogradourosService,
     public confirmationService: ConfirmationService,
     public messageService: MessageService
   ) {
     super(tiposLogradourosService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
     this.filtro.pagina = pagina;
     this.tiposLogradourosService.pesquisar(this.filtro)
       .then(resultado => {
         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.tiposLogradouros;
       })
       .catch(erro => {
         erro = 'Erro';
         this.loading = false;
       }
       );
   }

   aoMudarPagina(event: LazyLoadEvent) {
     const pagina = event.first / event.rows;
     this.filtro.params = new HttpParams();
     if (event.filters.descricao) {
       this.filtro.params = this.filtro.params.append('descricao', event.filters.descricao.value);
     }
     if (event.filters.id) {
       this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
     }
     if (event.filters.sigla) {
       this.filtro.params = this.filtro.params.append('sigla', event.filters.sigla.value);
     }
     this.pesquisar(pagina);
   }

   deleteResource(resource: TiposLogradouros) {
     this.confirmationService.confirm({
       accept: () => {
         this.delete(resource, this.deleteSucess, this.deleteFail);
       },
       reject: () => {

       }
     });
   }

   deleteSucess(messageService: MessageService) {
     console.log('deletado');
     messageService.add({ severity: 'success', summary: 'Successo', detail: 'Deletado Com Sucesso!' });
     this.pesquisar(0);
   }

   deleteFail(error: any, messageService: MessageService) {
     console.log('error');
     console.log(error.error[0].mensagemUsuario);
     messageService.add({ severity: 'error', summary: 'Erro', detail: error.error[0].mensagemUsuario });
     this.pesquisar(0);
   }
 }


