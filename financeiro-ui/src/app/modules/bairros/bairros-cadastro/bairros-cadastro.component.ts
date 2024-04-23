import { DialogService } from 'primeng/dynamicdialog';
import { MessageService, SelectItem } from "primeng/api";
import { Component, Injector } from "@angular/core";
import { Validators } from "@angular/forms";
import { BaseResourceFormComponent } from "../../../shared/components/base-resource-form/base-resource-form.component";

import { BairrosService } from "../bairros.service";
import { Bairros } from "./../../../shared/models/bairros";
import { CidadesModalComponent } from './../../cidades/cidades-modal/cidades-modal.component';

@Component({
  selector: "app-bairros-cadastro",
  templateUrl: "./bairros-cadastro.component.html",
  styleUrls: ["./bairros-cadastro.component.css"],
})

export class BairrosCadastroComponent extends BaseResourceFormComponent<Bairros> {
   botaoOnOf = false;

   constructor(
      protected bairrosService: BairrosService,
      protected injector: Injector,
      public dialogService: DialogService,
      public messageService: MessageService
   ) {
      super(injector, new Bairros(), bairrosService, Bairros.fromJson, messageService);
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
         id:[null],
         cidadesId: [null,[Validators.required]],
         nome: [null, [Validators.required, Validators.minLength(3)]],

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
    return "Cadastro de Novo Bairro";
  }

  protected editionPageTitle(): string {
    const bairrosName = this.resource.nome || "";
    this.botaoOnOf = true;
    return "Editando Bairro: " ;
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
      const resource: Bairros = Bairros.toJson(this.resourceForm.value);

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
      const resource: Bairros = Bairros.toJson(this.resourceForm.value);
      this.resourceService.update(resource).subscribe(
         (resource) => {
            this.actionsForSuccess(resource);
         },
         (error) => this.actionsForError(error)
      );
   }

}
