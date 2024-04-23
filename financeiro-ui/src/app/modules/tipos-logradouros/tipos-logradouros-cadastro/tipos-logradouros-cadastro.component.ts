import { MessageService } from 'primeng/api';
import { TiposLogradouros } from './../../../shared/models/tipos-logradouros';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposLogradourosService } from '../tipos-logradouros.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-logradouros-cadastro',
  templateUrl: './tipos-logradouros-cadastro.component.html',
  styleUrls: ['./tipos-logradouros-cadastro.component.css']
})
export class TiposLogradourosCadastroComponent extends BaseResourceFormComponent<TiposLogradouros> {

   constructor(
      protected tiposLogradourosService: TiposLogradourosService,
      protected injector: Injector) {

      super(injector, new TiposLogradouros(), tiposLogradourosService, TiposLogradouros.fromJson, new MessageService());
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
      id: [null],
      descricao: [null, [Validators.required, Validators.minLength(3)]],
      sigla: [null, [Validators.required, Validators.minLength(3)]],
      });
   }

   protected creationPageTitle(): string {
      return 'Cadastro de Novo Tipo Logradouro';
   }

   protected editionPageTitle(): string {
      const tiposLogradourosName = this.resource.descricao || '';
      return 'Editando Tipo Logradouro: ' + tiposLogradourosName;
   }
}
