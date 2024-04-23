import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

import { Cidades} from './../../../shared/models/cidades';
import { CidadesFiltro } from './../cidades-filtro';
import { CidadesService } from '../cidades.service';

@Component({
  selector: 'app-cidades-modal',
  templateUrl: './cidades-modal.component.html',
  styleUrls: ['./cidades-modal.component.css']
})

export class CidadesModalComponent extends BaseResourceListComponent<Cidades> {

   filtro = new CidadesFiltro();
   resources = [];
   loading = true;

   constructor(
     private cidadesService: CidadesService,
     public confirmationService: ConfirmationService,
     public messageService: MessageService,
     public ref: DynamicDialogRef
   ) {
     super(cidadesService, confirmationService, messageService);
   }

   pesquisar(pagina = 0) {
     this.filtro.pagina = pagina;
     this.cidadesService.pesquisar(this.filtro)
       .then(resultado => {
         //console.log(resultado);

         this.loading = false;
         this.filtro.totalRegistros = resultado.total;
         this.resources = resultado.cidades;
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

     this.pesquisar(pagina);
   }

   selecItem(cidades){
     this.ref.close(cidades);
   }

}
