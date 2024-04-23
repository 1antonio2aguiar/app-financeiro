import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { Ceps} from './../../../shared/models/ceps';
import { CepsFiltro } from '../ceps-filtro';
import { CepsService } from './../ceps.service';

@Component({
   selector: 'app-ceps-pesquisa',
   templateUrl: './ceps-pesquisa.component.html',
   styleUrls: ['./ceps-pesquisa.component.css']
})

export class CepsPesquisaComponent extends BaseResourceListComponent<Ceps> {
  filtro = new CepsFiltro();
  resources = [];
  loading = true;

   constructor(
         private cepsService: CepsService,
         public confirmationService: ConfirmationService,
         public messageService: MessageService
      ) {
      super(cepsService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;
      this.cepsService.pesquisar(this.filtro)
         .then(resultado => {
         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.ceps;
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

      if (event.filters.cep) {
         this.filtro.params = this.filtro.params.append('cep', event.filters.cep.value);
      }

      if (event.filters.cidade) {
         this.filtro.params = this.filtro.params.append('bairrosFilter.cidadesFilter.nome', event.filters.cidade.value);
      }

      if (event.filters.bairro) {
         this.filtro.params = this.filtro.params.append('bairrosFilter.nome', event.filters.bairro.value);
      }

      if (event.filters.logradouro) {
         this.filtro.params = this.filtro.params.append('logradourosFilter.nome', event.filters.logradouro.value);
      }

      this.pesquisar(pagina);
   }

   deleteResource(resource: Ceps) {
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
