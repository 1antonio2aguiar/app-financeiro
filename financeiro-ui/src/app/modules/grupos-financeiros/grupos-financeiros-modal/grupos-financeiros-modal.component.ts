import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

import { GruposFinanceiros } from 'src/app/shared/models/grupos-financeiros';
import { GruposFinanceirosService } from '../grupos-financeiros.service';
import { GruposFinanceirosFiltro } from '../grupos-financeiros-filtro';

@Component({
  selector: 'app-grupos-financeiros-modal',
  templateUrl: './grupos-financeiros-modal.component.html',
  styleUrls: ['./grupos-financeiros-modal.component.css']
})
export class GruposFinanceirosModalComponent extends BaseResourceListComponent<GruposFinanceiros>{
   filtro = new GruposFinanceirosFiltro();
   resources = [];
   loading = true;

   constructor(
      private gruposFinanceirosService: GruposFinanceirosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService,
      public ref: DynamicDialogRef
   ) {
      super(gruposFinanceirosService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;
      this.gruposFinanceirosService.pesquisar(this.filtro)
        .then(resultado => {
          //console.log(resultado);

          this.loading = false;
          this.filtro.totalRegistros = resultado.total;
          this.resources = resultado.gruposFinanceiros;
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

      if (event.filters.receitaDespesa) {
        this.filtro.params = this.filtro.params.append('receitaDespesa', event.filters.receitaDespesa.value);
      }

      if (event.filters.descricao) {
         this.filtro.params = this.filtro.params.append('descricao', event.filters.descricao.value);
      }

      this.pesquisar(pagina);
   }

    selecItem(gruposFinanceiros){
      this.ref.close(gruposFinanceiros);
   }
}
