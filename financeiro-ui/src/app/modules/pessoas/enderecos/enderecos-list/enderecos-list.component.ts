import { ConfirmationService, MessageService } from 'primeng/api';
import { Component } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { BaseResourceListComponent } from '../../../../shared/components/base-resource-list/base-resource-list.component';

import { environment } from 'src/environments/environment';

import { EnderecosService } from '../enderecos.service';
import { Enderecos } from './../../../../shared/models/enderecos';
import { EnderecosModalComponent } from '../enderecos-modal/enderecos-modal.component';

@Component({
  selector: 'app-enderecos-list',
  templateUrl: './enderecos-list.component.html',
  styleUrls: ['./enderecos-list.component.css']
})
export class EnderecosListComponent extends BaseResourceListComponent<Enderecos>{
   modalVisible = false;
  env = environment;

   // Retorna os dados da lista de enderecos aqui.
   ngOnInit(){
      this.loadListEnderecos();
   }

   constructor(
         private enderecosService: EnderecosService,
         public confirmationService: ConfirmationService,
         public messageService: MessageService,
         public dialogService: DialogService ,/*
         public ref: DynamicDialogRef,*/

      ) {
      super(enderecosService, confirmationService, messageService);
   }

   // Abre Modal enderecos - novo endereco
   showEnderecosModalNew($event) {
         this.env.currentActionGlobal = "NEW";
         this.modalVisible = true;
         const ref = this.dialogService.open(EnderecosModalComponent, {
         header: 'Informe os endereços ' , width: '70%'
      });

      ref.onClose.subscribe( () => {
         this.enderecosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
      });
   }

   // Abre Modal enderecos - edita endereco é o mesmo HTML, mas aqui vem com parametro id do endereco
   showEnderecosModalEdit(enderecoId) {
         this.env.currentActionGlobal = "EDIT";
         this.modalVisible = true;
         const ref = this.dialogService.open(EnderecosModalComponent, {
         header: 'Editando endereços ' , width: '70%'
      });

      this.enderecosService.buscaEndereco(enderecoId);

      ref.onClose.subscribe( () => {
         //ATUALIZA A LISTA DE ENDEREÇOS
         this.enderecosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
      });
   }

   showEnderecosModalDelete(enderecoId){
      this.env.currentActionGlobal = "DELETE";
      this.modalVisible = true;
      this.env.botaoOnOf = false;

      const ref = this.dialogService.open(EnderecosModalComponent, {
      header: 'CONFIRMA DELETAR ENDEREÇO? ' , width: '70%',
      //styleClass:
      contentStyle: {"background-color": "red", "font-size": "3px"},
      //      style: {"background-color": "blue"},

      });

      this.enderecosService.buscaEndereco(enderecoId);

      ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE ENDEREÇOS
      this.enderecosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
      });

   }

   // Retorna lista de enderecos e rederiza HTML da lista
   loadListEnderecos(){
      //console.log("1 - ATUALZANDO foi no loadListEnderecos " );
      this.enderecosService.enderecosChangeSubscribe(
      resources => {
         //console.log("2  - ATUALZANDO foi no loadListEnderecos " );
         this.resources = resources;

      })
   }

}
