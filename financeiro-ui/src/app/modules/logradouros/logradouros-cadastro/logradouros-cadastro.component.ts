import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators } from '@angular/forms';

import { Logradouros } from './../../../shared/models/logradouros';
import { LogradourosService } from '../logradouros.service';
import { TiposLogradourosService } from './../../tipos-logradouros/tipos-logradouros.service';
import { CidadesModalComponent } from '../../cidades/cidades-modal/cidades-modal.component';

import * as moment from 'moment';

@Component({
   selector: 'app-logradouros-cadastro',
   templateUrl: './logradouros-cadastro.component.html',
   styleUrls: ['./logradouros-cadastro.component.css']
})

export class LogradourosCadastroComponent extends BaseResourceFormComponent<Logradouros> {

   tiposLogradourosList: SelectItem[];

   botaoOnOf = false;
   ptBrLocale;

   constructor(
      protected logradourosService: LogradourosService,
      protected tiposLogradourosService: TiposLogradourosService,
      protected injector: Injector,
      public dialogService: DialogService,
      public messageService: MessageService
   ) {
      super(injector, new Logradouros(), logradourosService, Logradouros.fromJson, new MessageService());
      this.loadTiposLogradouros();
      this.ptBrLocale = this.loadLocale();
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
         id:[null],
         tiposLogradourosId: [null,[Validators.required]],
         cidadesId: [null,[Validators.required]],
         nome: [null, [Validators.required, Validators.minLength(1)]],
         observacao: [null],

         cidades: this.formBuilder.group({
            estados: this.formBuilder.group({
              uf: [null],
            }),
            id: [null, [Validators.required]],
            nome: [null],
         })
      });
   }

   protected creationPageTitle(): string {
      this.botaoOnOf = false;
      return 'Cadastro de Novo Logradouro';
   }

   protected editionPageTitle(): string {
      const logradourosName = this.resource.nome || '';
      this.botaoOnOf = true;
      return 'Editando Logradouro: ' ;
   }

   loadTiposLogradouros() {
      this.tiposLogradourosService
         .listAll()
         .then((tiposLogradouros) => {
            this.tiposLogradourosList = tiposLogradouros.map((c) =>
            ({ label: c.descricao, value: c.id }));
         })
         .catch((erro) => erro);
   }

   // Modal Cidade/estado
   showCidades($event) {
      const ref = this.dialogService.open(CidadesModalComponent, {
         header: 'Selecione a Cidade',
         width: '70%'
      });

      ref.onClose.subscribe((cidade) => {
         //console.log(cidade);

         this.resourceForm.patchValue({
            cidades: {
               id: cidade.id,
               nome: cidade.nome,
               estados: {
                  uf: cidade.estados.nome
               }
            },
            cidadesId: cidade.id
         });
      });
   }

   protected createResource() {
      const resource: Logradouros = Logradouros.toJson(this.resourceForm.value);
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
      const resource: Logradouros = Logradouros.toJson(this.resourceForm.value);
      this.resourceService.update(resource).subscribe(
         (resource) => {
            this.actionsForSuccess(resource);
         },
         (error) => this.actionsForError(error)
      );
   }

}
