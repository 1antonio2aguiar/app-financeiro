import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent, SortEvent} from 'primeng/api';
import { Component, ViewChild, Input, EventEmitter } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { DialogService } from 'primeng/dynamicdialog';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list/base-resource-list.component';
import { environment } from 'src/environments/environment';

import { ParcelasContratos } from 'src/app/shared/models/parcelas-contratos';
import { ParcelasContratosService } from '../parcelas-contratos.service';
import { ParcelasContratosCadastroComponent } from '../parcelas-contratos-cadastro/parcelas-contratos-cadastro.component';
import { ParcelasContratosFiltro } from '../parcelas-contratos-filtro';

@Component({
   selector: 'app-parcelas-contratos-pesquisa',
   templateUrl: './parcelas-contratos-pesquisa.component.html',
   styleUrls: ['./parcelas-contratos-pesquisa.component.css']
})

export class ParcelasContratosPesquisaComponent extends BaseResourceListComponent<ParcelasContratos> {
   filtro = new ParcelasContratosFiltro();

   modalVisible = false;
   env = environment;
   loading = true;
   @Input() contratoId: string;

   receitaDespesa="";
   grupoFinanceiro="";
   tipoGrupoFinanceiro="";
   totalRegistros=0;

   // Retorna os dados da lista de parcelas aqui.
   ngOnInit(){
      //console.log("AI OOOOO ESTA AQUI PARA RECUPERAR LISTA ", this.filtro.pagina)
      this.loadListParcelasContratos();
   }

   constructor(
      private parcelasContratosService: ParcelasContratosService,
      public confirmationService: ConfirmationService,
      public messageService: MessageService,
      public dialogService: DialogService

   ) {
      super(parcelasContratosService, confirmationService, messageService);
   }

   // Abre Modal Parcelas Contratos - nova parcela
   showParcelasContratosModalNew($event) {
      this.env.currentActionGlobal = "NEW";
      this.modalVisible = true;
      this.contratoId = (<HTMLSelectElement>document.getElementById('id')).value;

      this.parcelasContratosService.buscaMaxParcelaContrato(this.contratoId);

      const ref = this.dialogService.open(ParcelasContratosCadastroComponent, {
         header: 'Informe as parcelas' , width: '70%',
      });

      ref.onClose.subscribe( (r) => {
         this.filtro.pagina = 0

         //ATUALIZA A LISTA DE PARCELAS
         // parte de filtrar as parcelas do contrato
         this.filtro.params['updates'] = [ { op: 'a', param: 'contratoId', value: this.contratoId } ];
         this.parcelasContratosService.listAllYesPaginacao(this.filtro);
      });
   }

   showParcelasContratosModalEdit(parcelaId) {
      this.env.currentActionGlobal = "EDIT";
      this.modalVisible = true;
      const ref = this.dialogService.open(ParcelasContratosCadastroComponent, {
         header: 'Editando parcela ' , width: '70%'
      });

      this.parcelasContratosService.buscaParcelaContrato(parcelaId);

      ref.onClose.subscribe( () => {
         this.filtro.pagina = 0
         //ATUALIZA A LISTA DE PARCELAS
         // parte de filtrar as parcelas do contrato
         this.contratoId = (<HTMLSelectElement>document.getElementById('id')).value;
         this.filtro.params['updates'] = [ { op: 'a', param: 'contratoId', value: this.contratoId } ];
         this.parcelasContratosService.listAllYesPaginacao(this.filtro);
      });
   }

   showParcelasContratosModalDelete(parcelaId){
      this.env.currentActionGlobal = "DELETE";
      this.modalVisible = true;
      this.env.botaoOnOf = false;

      const ref = this.dialogService.open(ParcelasContratosCadastroComponent, {
         header: 'CONFIRMA DELETAR PARCELA? ' , width: '65%',
         //styleClass:
         contentStyle: {"background-color": "red", "font-size": "3px"},
         //      style: {"background-color": "blue"},
      });

      this.parcelasContratosService.buscaParcelaContrato(parcelaId);

      ref.onClose.subscribe( () => {
         this.filtro.pagina = 0
         //ATUALIZA A LISTA DE PARCELAS
         // parte de filtrar as parcelas do contrato
         this.contratoId = (<HTMLSelectElement>document.getElementById('id')).value;
         this.filtro.params['updates'] = [ { op: 'a', param: 'contratoId', value: this.contratoId } ];
         this.parcelasContratosService.listAllYesPaginacao(this.filtro);
      });
   }

   pesquisar(pagina = 0) {
      this.filtro.pagina = pagina;

      if(this.totalRegistros == 0){
         this.filtro.totalRegistros = 10;
      } else {
         this.filtro.totalRegistros = this.totalRegistros;
      }

      // parte de filtrar as parcelas do contrato
      this.contratoId = (<HTMLSelectElement>document.getElementById('id')).value;
      //this.filtro.params = this.filtro.params.append('contratoId', this.contratoId);

      this.parcelasContratosService.pesquisar(this.filtro)
      .then(resultado => {
         this.loading = false;
         this.resources = resultado;
      })
         .catch(erro => {
            console.log("Deu erro ")
                  erro = 'Erro';
                  this.loading = false;
         }
      );
   }

   aoMudarPagina(event: LazyLoadEvent) {
      const pagina = event.first / event.rows;

      this.filtro.params = new HttpParams();
      this.contratoId = (<HTMLSelectElement>document.getElementById('id')).value;
      if(this.contratoId == ''){
         this.contratoId = '0';
      }
      this.filtro.params = this.filtro.params.append('contratoId', this.contratoId);

      this.pesquisar(pagina);
   }

   // Retorna lista de parcelas e rederiza HTML da lista
   loadListParcelasContratos(){
      if(this.totalRegistros == 0){
         this.filtro.totalRegistros = 10;
      } else {
         this.filtro.totalRegistros = this.totalRegistros;
      }

      this.parcelasContratosService.parcelasContratosChangeSubscribe(
         resources => {
            this.totalRegistros = resources['totalElements'];
            this.resources = resources;

            this.receitaDespesa=(<HTMLSelectElement>document.getElementById('receitaDespesa')).value;
            this.grupoFinanceiro=(<HTMLSelectElement>document.getElementById('grupoFinanceiro')).value;
            this.tipoGrupoFinanceiro=(<HTMLSelectElement>document.getElementById('tipoGrupoFinanceiro')).value;
         }
      )
   }
}
