import { MessageService } from 'primeng/api';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';

import { GruposFinanceiros } from 'src/app/shared/models/grupos-financeiros';
import { GruposFinanceirosService } from '../grupos-financeiros.service';

@Component({
   selector: 'app-grupos-financeiros-cadastro',
   templateUrl: './grupos-financeiros-cadastro.component.html',
   styleUrls: ['./grupos-financeiros-cadastro.component.css']
})

export class GruposFinanceirosCadastroComponent extends BaseResourceFormComponent<GruposFinanceiros>{
   env = environment;

   receitaDespesa = [
      { value: 'RECEITA', selected: true, label: 'RECEITA' },
      { value: 'DESPESA', selected: false, label: 'DESPESA' }
   ];

   constructor(
      protected gruposFinanceirosService: GruposFinanceirosService,
      protected injector: Injector) {
      super(injector, new GruposFinanceiros(), gruposFinanceirosService, GruposFinanceiros.fromJson, new MessageService());
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
      id: [null],
      receitaDespesa: [null, [Validators.required]],
      descricao: [null, [Validators.required, Validators.minLength(2)]],
      });
   }

   protected creationPageTitle(): string {
      this.env.botaoOnOf = false;
      return 'Cadastro de Novo Grupo Financeiro';
   }

   protected editionPageTitle(): string {
      this.env.botaoOnOf = true;
      return 'Editando Grupo Financeiro';
   }
}
