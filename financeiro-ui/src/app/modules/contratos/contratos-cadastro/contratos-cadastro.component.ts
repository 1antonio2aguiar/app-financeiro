import { DialogService } from 'primeng/dynamicdialog';
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { MessageService } from 'primeng/api';
import { ChangeDetectorRef, AfterContentChecked} from '@angular/core';

import { environment } from 'src/environments/environment';

import { Contratos } from 'src/app/shared/models/contratos';
import { ContratosService } from '../contratos-service';
import { ParcelasContratosService } from '../parcelas-contratos/parcelas-contratos.service';
import { ParcelasContratosFiltro } from '../parcelas-contratos/parcelas-contratos-filtro';

@Component({
   selector: 'app-contratos-cadastro',
   templateUrl: './contratos-cadastro.component.html',
   styleUrls: ['./contratos-cadastro.component.css']
})

export class ContratosCadastroComponent extends BaseResourceFormComponent<Contratos>{
   ptBrLocale;
   selectedTab
   @Input() contratoId: string;
   env = environment;

   filtro = new ParcelasContratosFiltro();

   constructor(

      protected contratosService: ContratosService,
      protected parcelasContratosService: ParcelasContratosService,
      protected injector: Injector,
      public dialogService: DialogService,
      public messageService: MessageService,

      // Isso aqui e para corrigir o erro: ExpressionChangedAfterItHasBeenCheckedError
      private cdref: ChangeDetectorRef,

   ) {
      super(injector, new Contratos(), contratosService, Contratos.fromJson, new MessageService());
      this.ptBrLocale = this.loadLocale();
   }

   // Isso aqui e para corrigir o erro: ExpressionChangedAfterItHasBeenCheckedError
   ngAfterContentChecked() {
      this.cdref.detectChanges();
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
   })}

   // Ao clicar nas abas dispara este evento
   onTabChange(event) {
      // Pega o id do contrato
      this.contratoId = (<HTMLSelectElement>document.getElementById('id')).value;

      if (event.index == 1) {
         this.filtro.pagina = 0

         // parte de filtrar as parcelas do contrato
         this.filtro.params = this.filtro.params.append('contratoId', this.contratoId);
         this.parcelasContratosService.listAllYesPaginacao(this.filtro);
      }
   }
}
