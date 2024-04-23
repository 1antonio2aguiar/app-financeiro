import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators, FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { environment } from 'src/environments/environment';

import * as moment from 'moment';

import { Contratos } from 'src/app/shared/models/contratos';
import { ContratosService } from '../contratos-service';
import { TiposGruposFinanceirosModalComponent }
   from '../../tipos-grupos-financeiros/tipos-grupos-financeiros-modal/tipos-grupos-financeiros-modal.component';
import { PessoasModalComponent } from '../../tipos-grupos-financeiros/pessoas-modal/pessoas-modal.component';

@Component({
   selector: 'app-contratos-cadastro-view',
   templateUrl: './contratos-cadastro-view.component.html',
   styleUrls: ['./contratos-cadastro-view.component.css']
})

export class ContratosCadastroViewComponent extends BaseResourceFormComponent<Contratos> {
   env = environment;
   botaoOnOf = false;
   ptBrLocale;
   maxDate = new Date();
   isDisabledFild: boolean = false;

   situacao = [
      { value: 1, selected: false, label: 'ATIVO' },
      { value: 2, selected: false, label: 'INATIVO' },
      { value: 3, selected: false, label: 'PARALISADO' },
      { value: 4, selected: false, label: 'SUSPENSO' },
      { value: 5, selected: false, label: 'QUITADO' }
   ];

   constructor(
      protected contratosService: ContratosService,
      protected injector: Injector,
      public dialogService: DialogService ,
      public messageService: MessageService,
   ) {
      super(injector, new Contratos(), contratosService, Contratos.fromJson, new MessageService());
      this.ptBrLocale = this.loadLocale();
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
		   id: [null],
         numeroContrato: [null],
         valorContrato: [null, [Validators.required]],
         situacao: [1],
         observacao: [null],
         dataContrato: [null],
         dataInicio: [null],
         dataFim: [null],
         usuario:[null],

         tiposGruposFinanceiros: this.formBuilder.group({
            id: [null],
			   descricao: [null],
            gruposFinanceiros: this.formBuilder.group({
               receitaDespesa:[null],
               descricao:[null],
            }),
         }),

         pessoasContratante: this.formBuilder.group({
            id: [null],
			   nome: [null],
            cpfCnpj: [null],
         }),
         contratanteId: [null],

         pessoasContratado: this.formBuilder.group({
            id: [null],
			   nome: [null],
            cpfCnpj: [null],
         }),
         contratadoId: [null],
      })

      this.resourceForm.patchValue({
         usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
      });
   };

   // Modal tipos grupos financeiros
   showTiposGruposFinanceiros($event) {

      const ref = this.dialogService.open(TiposGruposFinanceirosModalComponent, {
         header: 'Selecione o tipo grupo financeiro',
         width: '70%'
      });

      ref.onClose.subscribe((tiposGruposFinanceiros) => {
         this.resourceForm.patchValue({
            tiposGruposFinanceiros: {
               id: tiposGruposFinanceiros.id,
               descricao: tiposGruposFinanceiros.descricao,

               gruposFinanceiros: {
                  receitaDespesa: tiposGruposFinanceiros.gruposFinanceiros.receitaDespesa,
                  descricao: tiposGruposFinanceiros.gruposFinanceiros.descricao
               }
            }
         });
      });
   }

   // Modal pessoa contratante
   showPessoaContratante($event) {

      const ref = this.dialogService.open(PessoasModalComponent, {
         header: 'Selecione o contratante',
         width: '70%'
      });

      ref.onClose.subscribe((pessoasContratante) => {
         this.resourceForm.patchValue({
            pessoasContratante: {
               id: pessoasContratante.id,
               nome: pessoasContratante.nome,
               cpfCnpj: pessoasContratante.cpfCnpj
            },
            contratanteId: pessoasContratante.id
         });
      });
   }

   // Modal pessoa contratado
   showPessoaContratado($event) {
      const ref = this.dialogService.open(PessoasModalComponent, {
         header: 'Selecione o contratado',
         width: '70%'
      });

      ref.onClose.subscribe((pessoasContratado) => {
         this.resourceForm.patchValue({
            pessoasContratado: {
               id: pessoasContratado.id,
               nome: pessoasContratado.nome,
               cpfCnpj: pessoasContratado.cpfCnpj
            },
            contratadoId: pessoasContratado.id
         });
      });
   }

   protected creationPageTitle(): string {
      this.botaoOnOf = false;
      //console.log("passou aqui ")
      return 'Novo Contrato';
   }

   protected editionPageTitle(): string {
      this.env.tabPanelOnOff = false;
      this.botaoOnOf = true;
      this.isDisabledFild = false;
      const stateFild = this.isDisabledFild ? 'disable' : 'enable';
      return 'Editando Contrato: ' ;
   }

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

   protected createResource() {

      const replacer = function (key, value) {

         if (key == "numeroContrato" || key == "valorContrato") {
            let newValue = "";
            for (var i = 0; i < value.length; i++) {
               if(value[i] != '.'){
                  if(newValue == ""){
                     newValue = value[i];
                  } else {
                     if(value[i] == ','){
                        newValue += '.';
                     } else{
                        newValue += value[i];
                     }
                  }
               }
            }
            return newValue
         }
         return value;
      };

      const resource: Contratos = this.jsonDataToResourceFn(this.resourceForm.value);
      // copia os dados de Contratos para contratos, que é uma variavel que recebe os dados do form no tipo json
      let contratos = JSON.stringify(resource,replacer);

      // Chama a funcao que grava
      this.contratosService.createContrato(contratos)
      .then(response => {
         this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Contrato inserido com sucesso!'});

         // Move o id para contratos
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

   protected updateResource() {

      // Logica para mudar a data registro de string para date mover para resourceform e mudar o formato.
      const replacer = function (key, value) {
         if (key == "dataContrato" || key == "dataInicio" || key == "dataFim") {
            if(value != null){
               return moment(this[key], 'DD-MM-YYYY').format();
            } else {
               return null;
            }
         } else {
            if (key == "numeroContrato" || key == "valorContrato") {
               if (key == "numeroContrato"){
                  value = value.toString();
               }
               let newValue = "";
               for (var i = 0; i < value.length; i++) {
                  if(value[i] != '.'){
                     if(newValue == ""){
                        newValue = value[i];
                     } else {
                        if(value[i] == ','){
                           newValue += '.';
                        } else{
                           newValue += value[i];
                        }
                     }
                  }
               }
               return newValue
            }
         }
         return value;
      };
      // ate aqui.

      const resource: Contratos = this.jsonDataToResourceFn(this.resourceForm.value);
      // copia os dados de Contratos(Entidade) para contratos(objeto do json)
      let contratos = JSON.stringify(resource,replacer);

      // Chama a funcao que grava/ faz update
      this.contratosService.updateContrato(contratos)
      .then(response => {
         this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Contrato atualizado com sucesso!'});

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

   public cpfcnpjmaskContratante = function () {
      var numbers = (<HTMLSelectElement>document.getElementById('cpfCnpjContratante')).value.match(/\d/g);
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

   public cpfcnpjmaskContratado = function () {
      var numbers = (<HTMLSelectElement>document.getElementById('cpfCnpjContratado')).value.match(/\d/g);
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

   public nroContratomask = function () {
      var numbers = (<HTMLSelectElement>document.getElementById('numeroContrato')).value.match(/\d/g);
      var numberLength = 0;
      if (numbers) {
        numberLength = numbers.join('').length;
      }

      if (numberLength <= 1){
         return  [/[0-9]/]
      }
      if (numberLength <= 2){
         return  [/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 3){
         return  [/[0-9]/,/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 4){
         return  [/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 5){
         return  [/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 6){
         return  [/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 7){
         return  [/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 8){
         return  [/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 9){
         return  [/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 10){
         return [/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/]
      }
   }

   public valorContratomask = function () {
      var numbers = (<HTMLSelectElement>document.getElementById('valorContrato')).value.match(/\d/g);
      var numberLength = 0;
      if (numbers) {
        numberLength = numbers.join('').length;
      }

      if (numberLength <= 2){
         return  [',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 3){
         return  [/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 4){
         return  [/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 5){
         return  [/[0-9]/,/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 6){
         return  [/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 7){
         return  [/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 8){
         return  [/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 9){
         return  [/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 10){
         return [/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
      if (numberLength <= 11){
         return [/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,'.',/[0-9]/,/[0-9]/,/[0-9]/,',',/[0-9]/,/[0-9]/]
      }
   }
}



