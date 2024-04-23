import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';

import { Logradouros } from './../../../shared/models/logradouros';
import { LogradourosService } from './../../logradouros/logradouros.service';
import { LogradourosFiltro } from './../logradouros-filtro';


@Component({
   selector: 'app-logradouros-modal',
   templateUrl: './logradouros-modal.component.html',
   styleUrls: ['./logradouros-modal.component.css']
})

export class LogradourosModalComponent extends BaseResourceListComponent<Logradouros> {

   filtro = new LogradourosFiltro();
   loading = true;

   // parte de filtrar logradouros da cidade
   idCidade;

   constructor(
      private logradourosService: LogradourosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService,
      public ref: DynamicDialogRef,
      public config: DynamicDialogConfig
   ) {
     super(logradourosService, confirmationService, messageService);
     // parte de filtrar logradouros da cidade
     this.idCidade = config.data.idCidade;
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;

      // parte de filtrar logradouros do distrito
      this.filtro.params = this.filtro.params.append('cidadesFilter.id',this.idCidade);

      this.logradourosService.pesquisar(this.filtro)
      .then(resultado => {
         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.logradouros;
      })
      .catch(erro => {
         erro = 'Erro';
         this.loading = false;
      });
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

      this.pesquisar(pagina);
   }

   selecItem(logradouros){
      this.ref.close(logradouros);
   }
}

