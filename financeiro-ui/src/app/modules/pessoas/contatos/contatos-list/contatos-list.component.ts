import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild, Input, EventEmitter } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { DialogService } from 'primeng/dynamicdialog';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list/base-resource-list.component';
import { environment } from 'src/environments/environment';

import { Contatos } from 'src/app/shared/models/contatos';
import { ContatosService } from '../contatos.service';
import { ContatosModalComponent } from '../contatos-modal/contatos-modal.component';

@Component({
   selector: 'app-contatos-list',
   templateUrl: './contatos-list.component.html',
   styleUrls: ['./contatos-list.component.css']
})

export class ContatosListComponent extends BaseResourceListComponent<Contatos> {

   modalVisible = false;
   env = environment;

   // Retorna os dados da lista de Contatos aqui.
   ngOnInit(){
      //console.log("AI OOOOO ESTA AQUI PARA RECUPERAR LISTA ")
      this.loadListContatos();
   }

   constructor(
      private contatosService: ContatosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService,
      public dialogService: DialogService

   ) {
      super(contatosService, confirmationService, messageService);
   }

   // Abre Modal Contatos - novo Contatos
   showContatosModalNew($event) {
      this.env.currentActionGlobal = "NEW";
      this.modalVisible = true;

      const ref = this.dialogService.open(ContatosModalComponent, {
         header: 'Informe os contatos' , width: '55%'
      });

      ref.onClose.subscribe( () => {
         //ATUALIZA A LISTA DE CONTATOS
         this.contatosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
      });
   }

   showContatosModalEdit(contatoId) {
      this.env.currentActionGlobal = "EDIT";
      this.modalVisible = true;
      const ref = this.dialogService.open(ContatosModalComponent, {
         header: 'Editando contatos ' , width: '55%'
      });

      //console.log("ID DO CONTATO " , contatoId);
      this.contatosService.buscaContato(contatoId);

      ref.onClose.subscribe( () => {
         //ATUALIZA A LISTA DE CONTATOS
         this.contatosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
      });
   }

   showContatosModalDelete(contatoId){
      this.env.currentActionGlobal = "DELETE";
      this.modalVisible = true;
      this.env.botaoOnOf = false;

      const ref = this.dialogService.open(ContatosModalComponent, {
         header: 'CONFIRMA DELETAR CONTATO? ' , width: '55%',
         //styleClass:
         contentStyle: {"background-color": "red", "font-size": "3px"},
         //      style: {"background-color": "blue"},
      });

      this.contatosService.buscaContato(contatoId);

      ref.onClose.subscribe( () => {
         //ATUALIZA A LISTA DE CONTATOS
         this.contatosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
      });
   }

   // Retorna lista de Contatos e rederiza HTML da lista
   loadListContatos(){
      //console.log("1 - ATUALZANDO foi no loadListContatos " );
      this.contatosService.contatosChangeSubscribe(
         resources => {
            //console.log("ATUALZANDO LISTA DE CONTATOS " , resources);
            this.resources = resources;

         }
      )
   }
}
