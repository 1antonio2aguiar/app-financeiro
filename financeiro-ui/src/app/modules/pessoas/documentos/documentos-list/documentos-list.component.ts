import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild, Input, EventEmitter } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { DialogService } from 'primeng/dynamicdialog';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list/base-resource-list.component';

import { environment } from 'src/environments/environment';

import { Documentos } from 'src/app/shared/models/documentos';
import { DocumentosService } from '../documentos.service';
import { DocumentosModalComponent } from '../documentos-modal/documentos-modal.component';

@Component({
  selector: 'app-documentos-list',
  templateUrl: './documentos-list.component.html',
  styleUrls: ['./documentos-list.component.css']
})
export class DocumentosListComponent extends BaseResourceListComponent<Documentos> {

  modalVisible = false;
  env = environment;

  // Retorna os dados da lista de documentos aqui.
  ngOnInit(){
    this.loadListDocumentos();
  }

  constructor(
    private documentosService: DocumentosService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService,
    public dialogService: DialogService,/*
    public ref: DynamicDialogRef,*/
  ) {
    super(documentosService, confirmationService, messageService);
  }

  // Abre Modal documentos - novo documento
  showDocumentosModalNew($event) {
    this.env.currentActionGlobal = "NEW";
    this.modalVisible = true;

    const ref = this.dialogService.open(DocumentosModalComponent, {
       header: 'Informe os Documentos' , width: '55%'
    });

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE Documentos
      this.documentosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });
  }

  showDocumentosModalEdit(documentoId) {
    this.env.currentActionGlobal = "EDIT";
    this.modalVisible = true;
    const ref = this.dialogService.open(DocumentosModalComponent, {
      header: 'Editando Documentos ' , width: '55%'
    });

    this.documentosService.buscaDocumento(documentoId)

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE Documentos
      this.documentosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });
  }

  showDocumentosModalDelete(documentoId){
    this.env.currentActionGlobal = "DELETE";
    this.modalVisible = true;
    this.env.botaoOnOf = false;

    const ref = this.dialogService.open(DocumentosModalComponent, {
      header: 'CONFIRMA DELETAR DOCUMENTO? ' , width: '55%',
      //styleClass:
      contentStyle: {"background-color": "red", "font-size": "3px"},
      //      style: {"background-color": "blue"},
    });

    this.documentosService.buscaDocumento(documentoId);

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE Documentos
      this.documentosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });
  }

  // Retorna lista de Documentos e rederiza HTML da lista
  loadListDocumentos(){
    //console.log("1 - ATUALZANDO foi no loadListDocumentos " );
    this.documentosService.documentosChangeSubscribe(
      resources => {
        //console.log("ATUALZANDO LISTA DE DOCUMENT0S " , resources);
        this.resources = resources;
      }
    )
  }


}
