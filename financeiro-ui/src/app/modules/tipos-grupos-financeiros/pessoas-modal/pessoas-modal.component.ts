import { Component, OnInit, ViewChild } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';

import { Pessoas } from 'src/app/shared/models/pessoas';
import { PessoasService } from '../../pessoas/pessoas.service';
import { TiposGruposFinanceirosFiltro } from '../tipos-grupos-financeiros-filtro';

@Component({
   selector: 'app-pessoas-modal',
   templateUrl: './pessoas-modal.component.html',
   styleUrls: ['./pessoas-modal.component.css']
})

export class PessoasModalComponent extends BaseResourceListComponent<Pessoas> {
   filtro = new TiposGruposFinanceirosFiltro();
   resources = [];
   loading = true;

   constructor(
      private pessoasService: PessoasService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService,
      public ref: DynamicDialogRef,
      public config: DynamicDialogConfig
   ) {
      super(pessoasService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;
      this.pessoasService.pesquisar(this.filtro)
      .then(resultado => {
         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.pessoas;
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
         this.filtro.params =
         this.filtro.params.append('nome', event.filters.nome.value);
      }

      if (event.filters.cpfCnpj) {
      this.filtro.params = this.filtro.params.append('cpfCnpj', event.filters.cpfCnpj.value);
      }

      this.pesquisar(pagina);
   }

   selecItem(pessoas){
      this.ref.close(pessoas);
   }
}
