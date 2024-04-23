import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { Contratos } from 'src/app/shared/models/contratos';
import { ContratosFiltro } from '../contratos-filtro';
import { ContratosService } from '../contratos-service';

@Component({
   selector: 'app-contratos-pesquisa',
   templateUrl: './contratos-pesquisa.component.html',
   styleUrls: ['./contratos-pesquisa.component.css']
})

export class ContratosPesquisaComponent extends BaseResourceListComponent<Contratos>{
   filtro = new ContratosFiltro();
   resources = [];
   loading = true;

   dataInicioMsk = {
      mask: [
         {
         mask: '00-00-0000'
         }
      ]
   };

   situacao = [
      { value: 1, selected: false, label: 'ATIVO' },
      { value: 2, selected: false, label: 'INATIVO' },
      { value: 3, selected: false, label: 'PARALISADO' },
      { value: 4, selected: false, label: 'SUSPENSO' },
      { value: 5, selected: false, label: 'QUITADO' }
   ];

   constructor(
      private contratosService: ContratosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService
      ) {
      super(contratosService, confirmationService, messageService, );
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;
      this.contratosService.pesquisar(this.filtro)
      .then(resultado => {
         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.contratos;
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

      if (event.filters.situacao) {
         this.filtro.params = this.filtro.params.append('situacao', event.filters.situacao.value);
      }

      if (event.filters.numeroContrato) {
         this.filtro.params = this.filtro.params.append('numeroContrato', event.filters.numeroContrato.value);
      }

      if (event.filters.dataInicio) {
        let temp = event.filters.dataInicio.value.split("-");
        temp = temp[2] + '-' + temp[1] + '-' + temp[0] /*+ ' 00:00:00'*/;
        //console.log("data 0: " + temp)

        //this.filtro.params = this.filtro.params.append('dataNascimento', temp);
        this.filtro.params = this.filtro.params.append('dataInicio', temp);
      }

      if (event.filters.receitaDespesa) {
         this.filtro.params = this.filtro.params.append
            ('tiposGruposFinanceirosFilter.gruposFinanceirosFilter.receitaDespesa',
            event.filters.receitaDespesa.value);
      }

      if (event.filters.grupoFinanceiro) {
         this.filtro.params = this.filtro.params.append
            ('tiposGruposFinanceirosFilter.gruposFinanceirosFilter.descricao',
            event.filters.grupoFinanceiro.value);
      }

      if (event.filters.tipoGrupoFinanceiro) {
         this.filtro.params = this.filtro.params.append('tiposGruposFinanceirosFilter.descricao',
            event.filters.tipoGrupoFinanceiro.value);
      }

      if (event.filters.contratante) {
         this.filtro.params = this.filtro.params.append('pessoasFilter.nome', event.filters.contratante.value);
      }

      if (event.filters.contratado) {
         this.filtro.params = this.filtro.params.append('pessoasFilter.nome', event.filters.contratado.value);
      }

      this.pesquisar(pagina);
   }

   deleteResource(resource: Contratos) {
      this.confirmationService.confirm({
         accept: () => {
            this.delete(resource, this.deleteSucess, this.deleteFail);
         },
            reject: () => {
         }
      });
   }

   deleteSucess(messageService: MessageService) {
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
