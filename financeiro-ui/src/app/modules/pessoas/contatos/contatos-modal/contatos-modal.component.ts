import { Component, Injector, Input, Output, EventEmitter } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form/base-resource-form.component';
import { MessageService} from 'primeng/api';
import { SelectItem } from 'primeng/api';
import { Validators, FormGroup } from '@angular/forms';
import { DialogService } from 'primeng/dynamicdialog';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { environment } from 'src/environments/environment';

import { Contatos } from 'src/app/shared/models/contatos';
import { ContatosService } from '../contatos.service';

@Component({
   selector: 'app-contatos-modal',
   templateUrl: './contatos-modal.component.html',
   styleUrls: ['./contatos-modal.component.css']
})

export class ContatosModalComponent extends BaseResourceFormComponent<Contatos>{
   tiposContatosList = [
      { value: 'TR', selected: false, label: 'TELEFONE RESIDENCIAL' },
      { value: 'CELULAR', selected: false, label: 'TELEFONE CELULAR' },
      { value: 'WHATSUP', selected: false, label: 'WHATSUP' },
      { value: 'EMAIL', selected: false, label: 'EMAIL' },
      { value: 'RECADO', selected: false, label: 'TELEFONE PARA RECADO' },
      { value: 'WEB', selected: false, label: 'PAGINA WEB' },
      { value: 'FAX', selected: false, label: 'FAX' },
      { value: 'OUTRO', selected: false, label: 'OUTRO' }
   ];

   //Move os dados da pessoas para o modal de cadastro de enderecos
   dadosPessoa=(<HTMLSelectElement>document.getElementById('nome')).value +' CPF: ' +
   (<HTMLSelectElement>document.getElementById('cpfCnpj')).value

   env = environment;
   contatoId = 0;

   constructor(
      protected contatosService: ContatosService,
      protected injector: Injector,
      public messageService: MessageService,
      public dialogService: DialogService ,
      public ref: DynamicDialogRef,
   ) {
      super(injector, new Contatos(), contatosService, Contatos.fromJson, new MessageService());

      this.buildResourceForm(); /*limpa o formulario/resourceForm */

      if (this.env.currentActionGlobal == "DELETE" || this.env.currentActionGlobal == "EDIT"){
         this.env.botaoOnOf = true;
      } else {
         this.env.botaoOnOf = false;
      }
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
         id: [null],
         pessoa: [(<HTMLSelectElement>document.getElementById('id')).value],
         contato:[null, [Validators.required, Validators.minLength(5)]],
         tipoContato: [null, [Validators.required]],
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
      this.contatosService.deleteContato(this.contatoId)
      .then(response => {
         this.buildResourceForm(); /*limpa o formulario/resorceForm*/
         this.ref.close();
      })
   }

   protected updateResource(){
      const resource: Contatos = this.jsonDataToResourceFn(this.resourceForm.value);

      // copia os dados de Contatos(classe) para contatos(variavel json)
      let contatos = JSON.stringify(resource);

      // Chama a funcao que faz update
      this.contatosService.updateContato(contatos)
      .then(response => {
         this.buildResourceForm(); /*limpa o formulario/resorceForm*/
         this.ref.close();
      })
   }

   protected createResource() {
      const resource: Contatos = this.jsonDataToResourceFn(this.resourceForm.value);
      // copia os dados de Contatos(classe) para contatos(variavel json)
      let contatos = JSON.stringify(resource);

      // Chama a funcao que insere
      this.contatosService.createContato(contatos)
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
      this.contatosService.contatosEditSubscribeId(
         resources => {

            this.contatoId = resources.id
            //console.log("TIPO ", resources.tipoContato)
            this.resourceForm.patchValue({
               id: resources.id,
               tipoContato: resources.tipoContato,
               contato: resources.contato
            })
         }
      )

      if(this.env.currentActionGlobal === "DELETE"){
         (<HTMLSelectElement>document.getElementById('tipoContatoDdw')).disabled = true;
         (<HTMLSelectElement>document.getElementById('contato')).disabled = true;
      }
   }
}
