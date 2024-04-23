import { MessageService } from "primeng/api";
import { Component, Injector } from "@angular/core";
import { BaseResourceFormComponent } from "../../../shared/components/base-resource-form/base-resource-form.component";
import { Validators } from "@angular/forms";

import { EstadosService } from "./../../estados/estados.service";
import { CidadesService } from "../cidades.service";
import { Cidades } from "./../../../shared/models/cidades";

@Component({
   selector: "app-cidades-cadastro",
   templateUrl: "./cidades-cadastro.component.html",
   styleUrls: ["./cidades-cadastro.component.css"],
})

export class CidadesCadastroComponent extends BaseResourceFormComponent<Cidades> {
   estadosList = [];

   constructor(
      protected cidadesService: CidadesService,
      protected estadosService: EstadosService,
      protected injector: Injector,
      public messageService: MessageService
   ) {
      super(injector, new Cidades(), cidadesService, Cidades.fromJson, messageService);
      this.loadEstados();
   }

   protected buildResourceForm() {
      this.resourceForm = this.formBuilder.group({
         id: [null],
         nome: [null, [Validators.required, Validators.minLength(5)]],
         uf: [null],
         estadosId: [null]
      });
   }

   protected creationPageTitle(): string {
      return "Cadastro de Nova Cidade";
   }

   protected editionPageTitle(): string {
      const cidadesName = this.resource.nome;
      return "Editando Cidade: " + cidadesName;
   }

   loadEstados() {
      this.estadosService
         .listAll()
         .then((estados) => {
            this.estadosList = estados.map((c) => ({ label: c.nome + ' - ' + c.uf, value: c.id }));
         })
         .catch((erro) => erro);
   }

   /*numberOnly(event): boolean {
      const charCode = event.which ? event.which : event.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
         return false;
      }
      return true;
   }*/

   protected createResource() {
      const resource: Cidades = Cidades.toJson(this.resourceForm.value);

      this.resourceService
         .create(resource)
         // tslint:disable-next-line:no-shadowed-variable
         .subscribe(
            (result) => {
               this.actionsForSuccess(result);
            },
            (error) => this.actionsForError(error)
         );
   }

   protected updateResource() {
      const resource: Cidades = Cidades.toJson(this.resourceForm.value);
      this.resourceService.update(resource).subscribe(
         // tslint:disable-next-line:no-shadowed-variable
         (resource) => {
            this.actionsForSuccess(resource);
         },
         (error) => this.actionsForError(error)
      );
   }
}

