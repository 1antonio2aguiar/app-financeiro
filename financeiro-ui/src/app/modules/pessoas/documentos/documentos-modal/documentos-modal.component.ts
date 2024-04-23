import { Component, Injector, Input, Output, EventEmitter } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form/base-resource-form.component';
import { MessageService} from 'primeng/api';
import { SelectItem } from 'primeng/api';
import { Validators, FormGroup, FormControlName } from '@angular/forms';
import { DialogService } from 'primeng/dynamicdialog';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import * as moment from 'moment';

import { environment } from 'src/environments/environment';

import { Documentos } from './../../../../shared/models/documentos';
import { DocumentosService } from '../documentos.service';

@Component({
   selector: 'app-documentos-modal',
   templateUrl: './documentos-modal.component.html',
   styleUrls: ['./documentos-modal.component.css'],
   // Isso aqui que resolve o problema de bloqueio de tela no modal
   providers: [DialogService],
})

export class DocumentosModalComponent extends BaseResourceFormComponent<Documentos> {

   // acho que isso e o gabriel que pos por conta do modal bloqueando tela
   @Output() closeModalEvent = new EventEmitter<boolean>();
   private documentos: Documentos;

   env = environment;
   documentoId = 0;
   tipoDocumento = {value: 0};
   maxDate = new Date();
   cpfCnpj = '';

   //Move os dados da pessoas para o modal de cadastro de enderecos
   dadosPessoa=(<HTMLSelectElement>document.getElementById('nome')).value +' CPF: ' +
   (<HTMLSelectElement>document.getElementById('cpfCnpj')).value

   tiposDocumentosList = [
      { value: 'RG', selected: false, label: 'REGISTRO GERAL - RG' },
      { value: 'CTPS', selected: false, label: 'CARTEIRA DE TRABALHO PROFISSIONAL' },
      { value: 'CNH', selected: false, label: 'CARTEIRA NACIONAL DE HABILITAÇÃO' },
      { value: 'RESERVISTA', selected: false, label: 'RESERVISTA' },
      { value: 'PASSAPORTE', selected: false, label: 'PASSAPORTE' },
      { value: 'TITELEITOR', selected: false, label: 'TÍTULO DE ELEITOR' },
      { value: 'INSCRIMUNICIPAL', selected: false, label: 'INSCRIÇÃO MUNICIPAL' },
      { value: 'OUTRO', selected: false, label: 'OUTRO' }
   ];

   ptBrLocale;

   constructor(
      protected documentosService: DocumentosService,
      protected injector: Injector,
      public messageService: MessageService,
      public dialogService: DialogService ,
      public ref: DynamicDialogRef,
   ) {
      super(injector, new Documentos(), documentosService, Documentos.fromJson, new MessageService());

      this.ptBrLocale = this.loadLocale();
      this.buildResourceForm(); /*limpa o formulario/resourceForm */

      if (this.env.currentActionGlobal != "DELETE"){
         this.env.botaoOnOf = false;
      } else {
         this.env.botaoOnOf = true;
      }

   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({

         id: [null],
         pessoa: [(<HTMLSelectElement>document.getElementById('id')).value],
         tipoDocumento:[null,Validators.required],
         numeroDocumento:[null,Validators.required],

         dataDocumento: [null],
         dataExpedicao: [null],
         dataValidade:  [null],
         observacao:[null],

      })
   }

   submitForm() {
      //this.submittingForm = true;
      if (this.env.currentActionGlobal === 'NEW') {
         // clicou no + (novo)
         this.createResource();
      } else {
         if (this.env.currentActionGlobal === 'EDIT') {
         // clicou no update
         this.updateResource();
         } else {
         // clicou no delete
         this.deleteResource();
         }
      }
   }

   protected deleteResource(){
      this.documentosService.deleteDocumento(this.documentoId)
      .then(response => {
         this.buildResourceForm(); /*limpa o formulario/resorceForm*/
         this.ref.close();
      })
   }

   protected updateResource(){

      // Logica para mudar a data registro de string para date mover para resourceform e mudar o formato.
      const replacer = function (key, value) {
         if (key == "dataDocumento" && value != null) {
            return moment(this[key], 'DD-MM-YYYY').format();
         }
         if (key == "dataExpedicao" && value != null) {
            return moment(this[key], 'DD-MM-YYYY').format();
         }
         if (key == "dataValidade" && value != null) {
            return moment(this[key], 'DD-MM-YYYY').format();
         }
         return value;
      };
      // ate aqui.

      const resource: Documentos = this.jsonDataToResourceFn(this.resourceForm.value);

      // copia os dados de Documentos(classe) para Documentos(variavel json)
      let documentos = JSON.stringify(resource,replacer);

      // Chama a funcao que faz update
      this.documentosService.updateDocumento(documentos)
      .then(response => {
         this.buildResourceForm(); /*limpa o formulario/resorceForm*/
         this.ref.close();
      })
   }

   protected createResource() {

      const resource: Documentos = this.jsonDataToResourceFn(this.resourceForm.value);
      // copia os dados de Documentos(classe) para Documentos(variavel json)
      let documentos = JSON.stringify(resource);

      // Chama a funcao que insere
      this.documentosService.createDocumento(documentos)
      .then(response => {
         this.buildResourceForm(); /*limpa o formulario/resorceForm*/
         this.ref.close();
      }).
         catch(error => {
         console.log(error);
         this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
      });
   }

   fecharModal(){
      this.ref.close();
   }

   // No edit retorna os dados aqui.
   ngOnInit(){
   this.documentosService.documentosEditSubscribeId(
      resources => {
         this.documentoId = resources.id
         this.resourceForm.patchValue({
            id: resources.id,
            tipoDocumento: resources.tipoDocumento,
            numeroDocumento: resources.numeroDocumento,
            dataDocumento: resources.dataDocumento,
            dataExpedicao: resources.dataExpedicao,
            dataValidade:  resources.dataValidade,
            observacao: resources.observacao,

         })
      })

      if(this.env.currentActionGlobal === "DELETE"){
         (<HTMLSelectElement>document.getElementById('numeroDocumento')).disabled = true;
         (<HTMLSelectElement>document.getElementById('observacao')).disabled = true;

         //(<HTMLSelectElement>document.getElementById('dataDocumento')).disabled = true;
         //(<HTMLSelectElement>document.getElementById('dataExpedicao')).disabled = true;
         //(<HTMLSelectElement>document.getElementById('dataValidade')).disabled = true;

      } else {
         if(this.env.currentActionGlobal === "EDIT"){
            this.env.botaoOnOf = true;
         }
      }
   }
}
