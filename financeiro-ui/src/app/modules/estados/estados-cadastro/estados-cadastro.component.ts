import { MessageService } from 'primeng/api';
import { Estados } from './../../../shared/models/estados';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { EstadosService } from '../estados.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-estados-cadastro',
  templateUrl: './estados-cadastro.component.html',
  styleUrls: ['./estados-cadastro.component.css']
})

export class EstadosCadastroComponent extends BaseResourceFormComponent<Estados> {

   paisList = [];
   paisesSelect;

   constructor(
      protected estadosService: EstadosService,
      protected injector: Injector) {
      super(injector, new Estados(), estadosService, Estados.fromJson, new MessageService());
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
      id: [null],
      paises:
      this.formBuilder.group({
         id: [null]
      }),
      nome: [null, [Validators.required, Validators.minLength(3)]],
      uf: [null, [Validators.required, Validators.minLength(2)]],
      });
   }

   protected creationPageTitle(): string {
      return 'Cadastro de Novo Estado';
   }

   protected editionPageTitle(): string {
      const estadosName = this.resource.nome || '';
      return 'Editando Estado: ' + estadosName;
   }
}
