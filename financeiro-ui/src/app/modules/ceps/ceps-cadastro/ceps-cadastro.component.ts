import { MessageService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators } from '@angular/forms';

import { Ceps } from './../../../shared/models/ceps';
import { CepsService } from '../ceps.service';
import { BairrosService } from './../../bairros/bairros.service';
import { LogradourosService } from './../../logradouros/logradouros.service';
import { CidadesModalComponent } from './../../cidades/cidades-modal/cidades-modal.component';
import { BairrosModalComponent } from './../../bairros/bairros-modal/bairros-modal.component';
import { LogradourosModalComponent } from './../../logradouros/logradouros-modal/logradouros-modal.component';

@Component({
   selector: 'app-ceps-cadastro',
   templateUrl: './ceps-cadastro.component.html',
   styleUrls: ['./ceps-cadastro.component.css']
})

export class CepsCadastroComponent extends BaseResourceFormComponent<Ceps> {

   bairroList = [];
   logradouroList = [];
   botaoOnOf = false;

   identificacao = [
      { value: 'U', label: 'Único' },
      { value: 'D', label: 'Direita' },
      { value: 'E', label: 'Esquerda' },
      { value: 'I', label: 'Impar' },
      { value: 'P', label: 'par' },
      { value: 'A', label: 'Ambos' }
   ];

   masks = {
      mask: [
         {
         mask: '00.000-000'
         }
      ]
   };

   constructor(
      protected cepsService: CepsService,
      protected bairrosService: BairrosService,
      protected logradourosService: LogradourosService,
      protected injector: Injector,
      public dialogService: DialogService )
   {
      super(injector, new Ceps(), cepsService, Ceps.fromJson, new MessageService());
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
         id: [null],
         cep: [null, [Validators.required]],
         numeroIni:[1],
         numeroFin:[999999999],
         identificacao: ["U"],

         cidades: this.formBuilder.group({
            estados: this.formBuilder.group({
              uf: [null],
              nome: [null],
            }),
            id: [null, [Validators.required]],
            nome: [null],
         }),

         bairrosId: [null],
         bairros: this.formBuilder.group({
            id: [null, [Validators.required, Validators.maxLength(10)]],
            nome:[null],
         }),

         logradourosId: [null],
         logradouros: this.formBuilder.group({
         id: [null, [Validators.required, Validators.maxLength(10)]],
         nome: [null],
            tiposLogradouros: this.formBuilder.group({
               sigla: [null],
            })
         })
      });
  }

   protected creationPageTitle(): string {
      this.botaoOnOf = false;
      return 'Cadastro de Novo Ceps';
   }

   protected editionPageTitle(): string {
      this.botaoOnOf = true;
      return 'Editando Ceps: ' ;
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
                  uf: cidade.estados.uf,
                  nome: cidade.estados.nome
               }
            },
            cidadesId: cidade.id
         });
      });
   }

   // Modal Bairro
   showBairros($event) {
      const ref = this.dialogService.open(BairrosModalComponent, {
         header: 'Selecione o Bairro',
         width: '70%',
         data:{
            // parte de filtrar bairros da cidade
            idCidade: this.resourceForm.get('cidades').get('id').value
         }
      });

      ref.onClose.subscribe((bairro) => {
         this.resourceForm.patchValue({
            bairros: {
               id: bairro.id,
               nome: bairro.nome
            },
            bairrosId: bairro.id
         });
      });
   }

   // Modal Logradouro
   showLogradouros($event) {
      const ref = this.dialogService.open(LogradourosModalComponent, {
         header: 'Selecione o Logradouro',
         width: '70%',
         data:{
            // parte de filtrar logradouros da cidade
            idCidade: this.resourceForm.get('cidades').get('id').value
         }
      });

      ref.onClose.subscribe((logradouro) => {
         this.resourceForm.patchValue({
            logradouros: {
               id: logradouro.id,
               nome: logradouro.nome,

               tiposLogradouros:{
                  sigla: logradouro.tiposLogradouros.sigla
               }
            },
            logradourosId: logradouro.id
         });
      });
   }

   protected createResource() {
      const resource: Ceps = Ceps.fromJson(this.resourceForm.value);
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
      const resource: Ceps = Ceps.toJson(this.resourceForm.value);
      this.resourceService.update(resource).subscribe(
         (resource) => {
            this.actionsForSuccess(resource);
         },
         (error) => this.actionsForError(error)
      );
   }

}
