import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form/base-resource-form.component';
import { MessageService} from 'primeng/api';
import { Validators, FormGroup } from '@angular/forms';
import { DialogService } from 'primeng/dynamicdialog';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { environment } from 'src/environments/environment';

import * as moment from 'moment';

import { ParcelasContratos } from 'src/app/shared/models/parcelas-contratos';
import { ParcelasContratosService } from '../parcelas-contratos.service';

@Component({
   selector: 'app-parcelas-contratos-cadastro',
   templateUrl: './parcelas-contratos-cadastro.component.html',
   styleUrls: ['./parcelas-contratos-cadastro.component.css']
})

export class ParcelasContratosCadastroComponent extends BaseResourceFormComponent<ParcelasContratos>{
   //Move os dados da pessoas para o modal de cadastro de parcelas
   dadosContrato= 'Contrato ' + (<HTMLSelectElement>document.getElementById('numeroContrato')).value +'/2022'

   receitaDespesa=(<HTMLSelectElement>document.getElementById('receitaDespesa')).value;
   grupoFinanceiro=(<HTMLSelectElement>document.getElementById('grupoFinanceiro')).value;
   tipoGrupoFinanceiro=(<HTMLSelectElement>document.getElementById('tipoGrupoFinanceiro')).value;

   ptBrLocale;
   maxDate = new Date();
   env = environment;
   parcelaContratoId = 0;
   vlrParcela: number = 0;
   vlrsValor: string = "";
   vlrJuros; vlrMulta; vlrCorrecao; vlrDesconto; vlrTotal;
   isDisabledDatas : boolean = true;

   constructor(
      protected parcelasContratosService: ParcelasContratosService,
      protected injector: Injector,
      public messageService: MessageService,
      public dialogService: DialogService ,
      public ref: DynamicDialogRef,
   ) {
      super(injector, new ParcelasContratos(), parcelasContratosService, ParcelasContratos.fromJson, new MessageService());
      this.ptBrLocale = this.loadLocale();

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
         parcela:[null, [Validators.required, Validators.minLength(1)]],
         valor: [null, [Validators.required]],
         juros: [null],
         multa: [null],
         correcao: [null],
         desconto: [null],
         valorPago:[null],
         dataVencimento: [{value: null,disabled: this.isDisabledDatas}, [Validators.required]],
         dataPagamento: [{value: null,disabled: this.isDisabledDatas}],
         documento: [null],
         bancoId: [1],
         observacao: [null],
         contratoId: [(<HTMLSelectElement>document.getElementById('id')).value],

         contratos: this.formBuilder.group({
            id: [null],
            numeroContrato: [null],
            tiposGruposFinanceiros: this.formBuilder.group({
               gruposFinanceiros: this.formBuilder.group({
                  receitaDespesa: [null],
                  descricao: [null],
               }),
               descricao: [null],
            })
         }),

         bancos: this.formBuilder.group({
            banco:[null],
            agencia:[null],
            nome:[null],
         })
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
      this.parcelasContratosService.deleteParcelaContrato(this.parcelaContratoId)
      .then(response => {
         this.buildResourceForm(); /*limpa o formulario/resorceForm*/
         this.ref.close();
      })
   }

   protected updateResource(){

      // Logica para mudar as datas de string para date mover para resourceform e mudar o formato.
      // Tambem troca os caracteres .(ponto) e ,(virgula) dos valores, convertendo de string para decimal
      const replacer = function (key, value) {
         if (key == "dataVencimento" || key == "dataPagamento") {
            if(value != null){
               return moment(this[key], 'DD-MM-YYYY').format();
            } else {
               return null;
            }
         }

         if (key == "valor" || key == "juros" || key == "multa" ||
             key == "correcao" || key == "desconto" || key == "valorPago") {
            this.vlrsValor = value;
            if(this.vlrsValor != null){
               this.vlrsValor = this.vlrsValor.replaceAll(".", "");
               this.vlrsValor =  this.vlrsValor.replace(",", ".");
               this.vlrValor = Number(this.vlrsValor);
               return this.vlrValor
            } else { return null }
         }
         return value
      }

      const resource: ParcelasContratos = this.jsonDataToResourceFn(this.resourceForm.value);
      // copia os dados de ParcelasContatos(classe) para parceçasContratos(variavel json)
      let parcelasContratos = JSON.stringify(resource,replacer);

      // Chama a funcao que faz update
      this.parcelasContratosService.updateParcelaContrato(parcelasContratos)
      .then(response => {
         this.buildResourceForm(); /*limpa o formulario/resorceForm*/
         this.ref.close();
      })
   }

   protected createResource() {
      const replacer = function (key, value) {
         if (key == "valor" || key == "juros" || key == "multa" ||
             key == "correcao" || key == "desconto" || key == "valorPago") {
            this.vlrsValor = value;
            if(this.vlrsValor != null){
               this.vlrsValor = this.vlrsValor.replaceAll(".", "");
               this.vlrsValor =  this.vlrsValor.replace(",", ".");
               this.vlrValor = Number(this.vlrsValor);
               return this.vlrValor
            } else { return null }
         }
         return value
      }
      const resource: ParcelasContratos = this.jsonDataToResourceFn(this.resourceForm.value);

      // copia os dados de ParcelasContatos(classe) para parceçasContratos(variavel json)
      let parcelasContratos = JSON.stringify(resource,replacer);

      // Chama a funcao que insere
      this.parcelasContratosService.createParcelaContrato(parcelasContratos)
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
      this.parcelasContratosService.parcelasContratosEditSubscribeId(
         resources => {
            //console.log("RES: ", resources.parcela)

            if(!resources.id){
               this.resourceForm.patchValue({
                  parcela: resources.parcela,
               })

            } else {
               this.parcelaContratoId = resources.id

               this.resourceForm.patchValue({
                  id: resources.id,

                  parcela: resources.parcela,
                  valor: resources.valor,
                  juros: resources.juros,
                  multa: resources.multa,
                  correcao: resources.correcao,
                  desconto: resources.desconto,
                  valorPago: resources.valorPago,
                  dataVencimento: resources.dataVencimento,
                  dataPagamento: resources.dataPagamento,
                  documento: resources.documento,
                  observacao: resources.observacao,

                  bancoId: resources.bancoId,
                  contratoId: resources.contratos.id
               })
            }
         }
      )

      if(this.env.currentActionGlobal === "DELETE"){
         (<HTMLSelectElement>document.getElementById('valor')).disabled = true;
         (<HTMLSelectElement>document.getElementById('juros')).disabled = true;
         (<HTMLSelectElement>document.getElementById('multa')).disabled = true;
         (<HTMLSelectElement>document.getElementById('correcao')).disabled = true;
         (<HTMLSelectElement>document.getElementById('desconto')).disabled = true;
         (<HTMLSelectElement>document.getElementById('documento')).disabled = true;
         (<HTMLSelectElement>document.getElementById('observacao')).disabled = true;
      } else {

         this.isDisabledDatas = true;
         this.isDisabledDatas = !this.isDisabledDatas;
         const stateDatas = this.isDisabledDatas ? 'disable' : 'enable';

         Object.keys(this.resourceForm.controls).forEach((controlName) => {
            switch ( controlName ) {
               case "dataVencimento":
                  this.resourceForm.controls[controlName][stateDatas](); break;
               case "dataPagamento":
                  this.resourceForm.controls[controlName][stateDatas](); break;
            }
         })
      }
   }

   /// Soma os valores e devolve em valor total
   somaVlrs = function somaVlrs(event: any){

      this.vlrsValor = this.resourceForm.get('valor').value;
      if(this.vlrsValor != null){
         //console.log("original ,", this.vlrsValor)
         this.vlrsValor = this.vlrsValor.replaceAll(".", "");
         //console.log("suprimi pontos ,", this.vlrsValor)
         this.vlrsValor =  this.vlrsValor.replace(",", ".");
         //console.log("stroca virgula ,", this.vlrsValor)
         this.vlrParcela = Number(this.vlrsValor);
         //console.log("resultado ,", this.vlrParcela)
      } else { this.vlrParcela = Number(0) }

      this.vlrsValor = this.resourceForm.get('juros').value;
      if(this.vlrsValor != null){
         this.vlrsValor = this.vlrsValor.replaceAll(".", "");
         this.vlrsValor =  this.vlrsValor.replace(",", ".");
         this.vlrJuros = Number(this.vlrsValor);
      } else { this.vlrJuros = Number(0) }

      this.vlrsValor = this.resourceForm.get('multa').value;
      if(this.vlrsValor != null){
         this.vlrsValor = this.vlrsValor.replaceAll(".", "");
         this.vlrsValor =  this.vlrsValor.replace(",", ".");
         this.vlrMulta = Number(this.vlrsValor);
      } else {  this.vlrMulta = Number(0) }

      this.vlrsValor = this.resourceForm.get('correcao').value;
      if(this.vlrsValor != null){
         this.vlrsValor = this.vlrsValor.replaceAll(".", "");
         this.vlrsValor =  this.vlrsValor.replace(",", ".");
         this.vlrCorrecao = Number(this.vlrsValor);
      } else { this.vlrCorrecao = Number(0) }

      this.vlrsValor = this.resourceForm.get('desconto').value;
      if(this.vlrsValor != null){
         this.vlrsValor = this.vlrsValor.replaceAll(".", "");
         this.vlrsValor =  this.vlrsValor.replace(",", ".");
         this.vlrDesconto = Number(this.vlrsValor);
      } else { this.vlrDesconto = Number(0) }

      this.vlrTotal = 0;
      this.vlrTotal = Number((this.vlrParcela + this.vlrJuros + this.vlrMulta + this.vlrCorrecao)
                              - (this.vlrDesconto)).toFixed(2);
      //console.log("Valor Total ,", this.vlrTotal)
      this.resourceForm.patchValue({
         valorPago: this.vlrTotal
      });

      (<HTMLSelectElement>document.getElementById('juros')).disabled = true;
      (<HTMLSelectElement>document.getElementById('multa')).disabled = true;
      (<HTMLSelectElement>document.getElementById('correcao')).disabled = true;
      (<HTMLSelectElement>document.getElementById('desconto')).disabled = true;
   };

   public valorMask = function () {

      var numbers = (<HTMLSelectElement>document.getElementById('valor')).value.match(/\d/g);

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

   public jurosMask = function () {

      var numbers = (<HTMLSelectElement>document.getElementById('juros')).value.match(/\d/g);

      var numberLength = 0;
      if (numbers) {
        numberLength = numbers.join('').length;
      }

      if (numberLength <= 1){
         return  [',',/[0-9]/]
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

   public multaMask = function () {

      var numbers = (<HTMLSelectElement>document.getElementById('multa')).value.match(/\d/g);

      var numberLength = 0;
      if (numbers) {
        numberLength = numbers.join('').length;
      }

      if (numberLength <= 1){
         return  [',',/[0-9]/]
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

   public correcaoMask = function () {

      var numbers = (<HTMLSelectElement>document.getElementById('correcao')).value.match(/\d/g);

      var numberLength = 0;
      if (numbers) {
        numberLength = numbers.join('').length;
      }

      if (numberLength <= 1){
         return  [',',/[0-9]/]
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

   public descontoMask = function () {

      var numbers = (<HTMLSelectElement>document.getElementById('desconto')).value.match(/\d/g);

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

   public valorPagoMask = function () {

      var numbers = (<HTMLSelectElement>document.getElementById('valorPago')).value.match(/\d/g);

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
