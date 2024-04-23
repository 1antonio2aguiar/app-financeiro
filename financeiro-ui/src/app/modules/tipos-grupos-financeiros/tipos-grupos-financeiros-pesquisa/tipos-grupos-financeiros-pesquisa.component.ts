import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { TiposGruposFinanceiros } from 'src/app/shared/models/tipos-grupos-financeiros';
import { TiposGruposFinanceirosService } from '../tipos-grupos-financeiros.service';
import { TiposGruposFinanceirosFiltro } from '../tipos-grupos-financeiros-filtro';

@Component({
   selector: 'app-tipos-grupos-financeiros-pesquisa',
   templateUrl: './tipos-grupos-financeiros-pesquisa.component.html',
   styleUrls: ['./tipos-grupos-financeiros-pesquisa.component.css']
})

export class TiposGruposFinanceirosPesquisaComponent extends
   BaseResourceListComponent<TiposGruposFinanceiros>{
   filtro = new TiposGruposFinanceirosFiltro();
   resources = [];
   loading = true;

   constructor(
      private tiposGruposFinanceirosService: TiposGruposFinanceirosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService
   ) {
      super(tiposGruposFinanceirosService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;
      this.tiposGruposFinanceirosService.pesquisar(this.filtro)
      .then(resultado => {
         console.table(resultado);
         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.tiposGruposFinanceiros;
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
         this.filtro.params =
         this.filtro.params.append('gruposFinanceirosFilter.receitaDespesa', event.filters.receitaDespesa.value);
      }

      if (event.filters.gfDescricao) {
         this.filtro.params =
         this.filtro.params.append('gruposFinanceirosFilter.descricao', event.filters.gfDescricao.value);
      }

      if (event.filters.descricao) {
         this.filtro.params = this.filtro.params.append('descricao', event.filters.descricao.value);
      }

      this.pesquisar(pagina);
   }

   deleteResource(resource: TiposGruposFinanceiros) {
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
