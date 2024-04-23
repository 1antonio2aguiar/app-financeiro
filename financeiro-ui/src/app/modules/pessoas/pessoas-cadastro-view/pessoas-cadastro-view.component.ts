import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators, FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { environment } from 'src/environments/environment';

import { PessoasService } from '../pessoas.service';
import { Pessoas } from './../../../shared/models/pessoas';

//import { TiposPessoasService } from './../../tipos-pessoas/tipos-pessoas.service';
//import { DistritosModalComponent } from './../../distritos/distritos-modal/distritos-modal.component';

import * as moment from 'moment';

@Component({
   selector: 'app-pessoas-cadastro-view',
   templateUrl: './pessoas-cadastro-view.component.html',
   styleUrls: ['./pessoas-cadastro-view.component.css'],

})

export class PessoasCadastroViewComponent extends BaseResourceFormComponent<Pessoas> {

   @Input() pessoaId: number;
   env = environment;
   botaoOnOf = false;
   maxDate = new Date();
   isDisabledFild: boolean = true;

   genero = [
      { value: 'M', selected: false, label: 'MASCULINO' },
      { value: 'F', selected: false, label: 'FEMININO' }
   ];

   fisicaJuridica = [
      { value: 'F', selected: true, label: 'FÍSICA' },
      { value: 'J', selected: false, label: 'JURÍDICA' }
   ];

   estadoCivil = [
      { value: 'A', selected: false, label: 'AMAZIADO(A)' },
      { value: 'C', selected: false, label: 'CASADO(A)' },
      { value: 'S', selected: false, label: 'SOLTEIRO(A)' },
      { value: 'U', selected: false, label: 'UNIÃO ESTÁVEL' },
      { value: 'V', selected: false, label: 'VIUVO(A)' },
      { value: 'O', selected: false, label: 'OUTRO' }
   ];

   situacao = [
      { value: 1, selected: false, label: 'ATIVO' },
      { value: 2, selected: false, label: 'INATIVO' },
      { value: 3, selected: false, label: 'PARALISADO' },
      { value: 4, selected: false, label: 'SUSPENSO' }
   ];

   ptBrLocale;

   constructor(
      protected pessoasService: PessoasService,
      protected injector: Injector,
      public dialogService: DialogService ,
      public messageService: MessageService,
   ) {
      super(injector, new Pessoas(), pessoasService, Pessoas.fromJson, new MessageService());
      this.ptBrLocale = this.loadLocale();
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
		   id: [null],
		   nome: [null, [Validators.required, Validators.minLength(5)]],
		   fisicaJuridica: [null, Validators.required],
         dataRegistro: [{value: null,disabled: this.isDisabledFild}],
		   observacao: [null],

         /*tiposPessoas:
            this.formBuilder.group({
            descricao:[null],
            id: [null],
         }),*/

			cpfCnpj: [{value: null,disabled: this.isDisabledFild},Validators.required],
			genero: [null],
			estadoCivil:[null],
		   situacao: [1],

         nomeFantasia: [{value: null,disabled: this.isDisabledFild}],
         objetoSocial: [{value: null,disabled: this.isDisabledFild}],
         tipoEmpresaId: [1]
      })
   };

   onChange(event) {
      this.isDisabledFild = false;
      const stateFild = this.isDisabledFild ? 'disable' : 'enable';
      this.validaEntradas(stateFild,event.value)

      if(event.value == 'F'){
         (<HTMLSelectElement>document.getElementById('nomeFantasia')).disabled = true;
         (<HTMLSelectElement>document.getElementById('objetoSocial')).disabled = true;
      } else {
         // Desbloqueia
         (<HTMLSelectElement>document.getElementById('nomeFantasia')).disabled = false;
         (<HTMLSelectElement>document.getElementById('objetoSocial')).disabled = false;
      }
   };

   public cpfcnpjmask = function () {
      var numbers = (<HTMLSelectElement>document.getElementById('cpfCnpj')).value.match(/\d/g);
      var numberLength = 0;
      if (numbers) {
        numberLength = numbers.join('').length;
      }
      if (numberLength <= 11) {
        return [/[0-9]/, /[0-9]/, /[0-9]/, '.', /[0-9]/, /[0-9]/, /[0-9]/, '.', /[0-9]/, /[0-9]/, /[0-9]/, '-', /[0-9]/, /[0-9]/];
      } else {
        return [/[0-9]/, /[0-9]/, '.', /[0-9]/, /[0-9]/, /[0-9]/, '.', /[0-9]/, /[0-9]/, /[0-9]/, '/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/, '-', /[0-9]/, /[0-9]/];
      }
   }

   protected creationPageTitle(): string {
      this.env.botaoOnOf = true;
      //console.log("passou aqui ")
      return 'Novo Cadastro de Pessoa';
   }

   protected editionPageTitle(): string {
      var numbers = (<HTMLSelectElement>document.getElementById('cpfCnpj')).value.match(/\d/g);
         this.env.tabPanelOnOff = false;
         this.env.botaoOnOf = true;
         this.isDisabledFild = false;
         const stateFild = this.isDisabledFild ? 'disable' : 'enable';

      if (numbers != null){
         if (numbers.length == 11){
            this.validaEntradas(stateFild,"F")
         } else {
            this.validaEntradas(stateFild,"J")
         }
      }

      return 'Editando Pessoas: ' ;
   }

   protected validaEntradas(stateFild,fisjur){
      if(fisjur == "F"){
         Object.keys(this.resourceForm.controls).forEach((controlName) => {
            switch ( controlName ) {
               case "cpfCnpj":
                  this.resourceForm.controls[controlName][stateFild](); break;
               case "dataRegistro":
                  this.resourceForm.controls[controlName][stateFild](); break;
            }
         })
      } else {
         Object.keys(this.resourceForm.controls).forEach((controlName) => {
            switch ( controlName ) {
               case "cpfCnpj":
                  this.resourceForm.controls[controlName][stateFild](); break;
               case "dataRegistro":
                  this.resourceForm.controls[controlName][stateFild](); break;
               case "nomeFantasia":
                  this.resourceForm.controls[controlName][stateFild](); break;
               case "objetoSocial":
                  this.resourceForm.controls[controlName][stateFild](); break;
            }
         })
      }
   };

   submitForm() {
      //this.submittingForm = true;
      if (this.currentAction === 'new') {
         // clicou no + (novo)
         this.createResource();
      } else {
         // clicou no update
         this.updateResource();
      }
   }

   protected updateResource() {

      // Logica para mudar a data registro de string para date mover para resourceform e mudar o formato.
      const replacer = function (key, value) {
         if (key == "dataRegistro") {
            return moment(this[key], 'DD-MM-YYYY').format();
         }
         return value;
      };
      // ate aqui.

      const resource: Pessoas = this.jsonDataToResourceFn(this.resourceForm.value);
      // copia os dados de Pessoas para pessoas
      let pessoas = JSON.stringify(resource,replacer);
      //console.log("Pessoas ", pessoas);

      // Chama a funcao que grava/ faz update
      this.pessoasService.updatePessoa(pessoas)
      .then(response => {
         this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Pessoa atualizada com sucesso!'});

         // redireciona para lista
         const baseComponentPath = this.route.snapshot.parent.url[0].path;
         this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
            console.log(this.router);
            return this.router.navigate(["/" + baseComponentPath]);
         });
      }).
         catch(error => {
         console.log(error);
         console.log('Falhou a tentativa de gravar');
         this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
      });
   }

   protected createResource() {
      const resource: Pessoas = this.jsonDataToResourceFn(this.resourceForm.value);
      // copia os dados de Pessoas para pessoas
      let pessoas = JSON.stringify(resource/*,replacer*/);

      // Chama a funcao que grava
      this.pessoasService.createPessoa(pessoas)
      .then(response => {
         this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Pessoa inserida com sucesso!'});

         // Move o id para pessoas
         this.resourceForm.patchValue({
            id: response.id
         });

         this.env.tabPanelOnOff = false;

         // redireciona para lista
         /*const baseComponentPath = this.route.snapshot.parent.url[0].path;
         this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
               console.log(this.router);
               return this.router.navigate(["/" + baseComponentPath]);
            });
         }).
         catch(error => {
         console.log(error, 'Falhou a tentativa de inserir');
         this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario}); */
      });
   }

}

