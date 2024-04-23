import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { EstadosService } from './../estados.service';
import { Estados } from './../../../shared/models/estados';
import { EstadosFiltro } from './../estados-filtro';

@Component({
  selector: 'app-estados-pesquisa',
  templateUrl: './estados-pesquisa.component.html',
  styleUrls: ['./estados-pesquisa.component.css']
})

export class EstadosPesquisaComponent extends BaseResourceListComponent<Estados> {
   filtro = new EstadosFiltro();
   resources = [];
   loading = true;
   constructor(
      private estadosService: EstadosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService
   ) {
      super(estadosService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;
      this.estadosService.pesquisar(this.filtro)
      .then(resultado => {
         console.table(resultado);
         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.estados;
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

      if (event.filters.id) {
         this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
      }

      if (event.filters.nome) {
         this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
      }

      if (event.filters.uf) {
         this.filtro.params = this.filtro.params.append('uf', event.filters.uf.value);
      }

      this.pesquisar(pagina);
   }

   deleteResource(resource: Estados) {
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
