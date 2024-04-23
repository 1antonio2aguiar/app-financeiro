import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { LogradourosFiltro } from '../logradouros-filtro';
import { LogradourosService } from './../logradouros.service';
import { Logradouros} from './../../../shared/models/logradouros';

@Component({
  selector: 'app-logradouros-pesquisa',
  templateUrl: './logradouros-pesquisa.component.html',
  styleUrls: ['./logradouros-pesquisa.component.css']
})

export class LogradourosPesquisaComponent extends BaseResourceListComponent<Logradouros> {
   filtro = new LogradourosFiltro();
   resources = [];
   loading = true;

   constructor(
      private logradourosService: LogradourosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService
      ) {
      super(logradourosService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
         this.filtro.pagina = pagina;
         this.logradourosService.pesquisar(this.filtro)
         .then(resultado => {
            this.loading = false;
            this.filtro.totalRegistros = resultado.total;
            this.resources = resultado.logradouros;
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

      if (event.filters.cidade) {
         this.filtro.params = this.filtro.params.append('cidadesFilter.nome', event.filters.cidade.value);
      }

      if (event.filters.nome) {
         this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
      }

      this.pesquisar(pagina);
   }

   deleteResource(resource: Logradouros) {
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
