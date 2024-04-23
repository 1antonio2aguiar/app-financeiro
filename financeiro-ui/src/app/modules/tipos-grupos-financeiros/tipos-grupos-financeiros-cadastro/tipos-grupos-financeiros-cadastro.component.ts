import { DialogService } from 'primeng/dynamicdialog';
import { MessageService, SelectItem } from "primeng/api";
import { Component, Injector } from "@angular/core";
import { Validators } from "@angular/forms";
import { BaseResourceFormComponent } from "../../../shared/components/base-resource-form/base-resource-form.component";

import { TiposGruposFinanceiros } from 'src/app/shared/models/tipos-grupos-financeiros';
import { TiposGruposFinanceirosService } from '../tipos-grupos-financeiros.service';
import { GruposFinanceirosModalComponent } from '../../grupos-financeiros/grupos-financeiros-modal/grupos-financeiros-modal.component';

@Component({
   selector: 'app-tipos-grupos-financeiros-cadastro',
   templateUrl: './tipos-grupos-financeiros-cadastro.component.html',
   styleUrls: ['./tipos-grupos-financeiros-cadastro.component.css']
})

export class TiposGruposFinanceirosCadastroComponent extends BaseResourceFormComponent<TiposGruposFinanceiros> {
   botaoOnOf = false;

   constructor(
      protected tiposGruposFinanceirosService: TiposGruposFinanceirosService,
      protected injector: Injector,
      public dialogService: DialogService,
      public messageService: MessageService
   ) {
      super(injector, new TiposGruposFinanceiros(),
         tiposGruposFinanceirosService,
         TiposGruposFinanceiros.fromJson, messageService);
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
         id:[null],
         grupoFinanceiroId: [null,[Validators.required]],
         descricao: [null, [Validators.required, Validators.minLength(3)]],

         gruposFinanceiros: this.formBuilder.group({
            id: [null, [Validators.required]],
            receitaDespesa: [null],
            descricao: [null],
         })
      });
   }

   protected creationPageTitle(): string {
      this.botaoOnOf = false;
      return "Cadastro de Novo Tipo Grupo Financeiro";
   }

   protected editionPageTitle(): string {
      const bairrosName = this.resource.descricao || "";
      this.botaoOnOf = true;
      return "Editando Tipo Grupo Financeiro: " ;
   }

   // Modal Grupo financeiro
   showGruposFinanceiros($event) {
      const ref = this.dialogService.open(GruposFinanceirosModalComponent, {
         header: 'Selecione o Grupo Financeiro',
         width: '70%'
      });

      ref.onClose.subscribe((grupoFinanceiro) => {
         //console.log(cidade);

         this.resourceForm.patchValue({
            gruposFinanceiros: {
               id: grupoFinanceiro.id,
               receitaDespesa: grupoFinanceiro.receitaDespesa,
               descricao: grupoFinanceiro.descricao,
            },
            grupoFinanceiroId: grupoFinanceiro.id
         });
      });
   }

   protected createResource() {
      const resource: TiposGruposFinanceiros = TiposGruposFinanceiros.toJson(this.resourceForm.value);

      this.resourceService
      .create(resource)
      .subscribe(
         (result) => {
            this.actionsForSuccess(result);
         },
         (error) => this.actionsForError(error)
      );
   }

   protected updateResource() {
      const resource: TiposGruposFinanceiros = TiposGruposFinanceiros.toJson(this.resourceForm.value);
      this.resourceService.update(resource).subscribe(
         (resource) => {
            this.actionsForSuccess(resource);
         },
         (error) => this.actionsForError(error)
      );
   }
}
