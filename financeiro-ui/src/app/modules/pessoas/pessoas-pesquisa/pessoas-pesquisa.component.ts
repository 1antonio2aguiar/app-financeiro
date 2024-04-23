import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { Pessoas } from 'src/app/shared/models/pessoas';
import { PessoasFiltro } from '../pessoas-filtro';
import { PessoasService } from '../pessoas.service';

@Component({
  selector: 'app-pessoas-pesquisa',
  templateUrl: './pessoas-pesquisa.component.html',
  styleUrls: ['./pessoas-pesquisa.component.css']
})

export class PessoasPesquisaComponent extends BaseResourceListComponent<Pessoas> {

   filtro = new PessoasFiltro();
   resources = [];
   loading = true;

   dataRegistroMsk = {
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
      { value: 4, selected: false, label: 'SUSPENSO' }
   ];

   constructor(
      private pessoasService: PessoasService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService
      ) {
      super(pessoasService, confirmationService, messageService, );
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
        this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
      }

      if (event.filters.cpfCnpj) {
        this.filtro.params = this.filtro.params.append('cpfCnpj', event.filters.cpfCnpj.value);
      }

      if (event.filters.dataRegistro) {
        let temp = event.filters.dataRegistro.value.split("-");
        temp = temp[2] + '-' + temp[1] + '-' + temp[0] /*+ ' 00:00:00'*/;
        //console.log("data 0: " + temp)

        //this.filtro.params = this.filtro.params.append('dataNascimento', temp);
        this.filtro.params = this.filtro.params.append('dataRegistro', temp);
      }

      if (event.filters.situacao) {
         this.filtro.params = this.filtro.params.append('situacao', event.filters.situacao.value);
      }

      this.pesquisar(pagina);
   }

   deleteResource(resource: Pessoas) {
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
