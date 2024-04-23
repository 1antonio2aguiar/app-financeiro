import { DialogService } from 'primeng/dynamicdialog';
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { MessageService } from 'primeng/api';
import { ChangeDetectorRef, AfterContentChecked} from '@angular/core';

import { environment } from 'src/environments/environment';

import { PessoasService } from '../pessoas.service';
import { Pessoas } from './../../../shared/models/pessoas';
import { EnderecosService } from '../enderecos/enderecos.service';
import { ContatosService } from '../contatos/contatos.service';
import { DocumentosService } from '../documentos/documentos.service';
//import { EmpresasService } from '../empresas/empresas.service';

@Component({
   selector: 'app-pessoas-cadastro',
   templateUrl: './pessoas-cadastro.component.html',
   styleUrls: ['./pessoas-cadastro.component.css']
})

export class PessoasCadastroComponent extends BaseResourceFormComponent<Pessoas> {

   ptBrLocale;
   selectedTab
   @Input() pessoaId: string;
   env = environment;

   constructor(

         protected pessoasService: PessoasService,
         protected enderecosService: EnderecosService,
         protected contatosService: ContatosService,
         protected documentosService: DocumentosService,
         //protected empresasService: EmpresasService,
         protected injector: Injector,
         public dialogService: DialogService,
         public messageService: MessageService,

         // Isso aqui e para corrigir o erro: ExpressionChangedAfterItHasBeenCheckedError
         private cdref: ChangeDetectorRef,

   ) {

      super(injector, new Pessoas(), pessoasService, Pessoas.fromJson, new MessageService());
      this.ptBrLocale = this.loadLocale();
   }

   // Isso aqui e para corrigir o erro: ExpressionChangedAfterItHasBeenCheckedError
   ngAfterContentChecked() {
      this.cdref.detectChanges();
   }

   protected buildResourceForm() {
         this.resourceForm = this.formBuilder.group({
      })
   }

   // Ao clicar nas abas dispara este evento
   onTabChange(event) {
      this.pessoaId = (<HTMLSelectElement>document.getElementById('id')).value;

      if (event.index == 1) {
         this.enderecosService.listAll(this.pessoaId);
      }

      if(event.index == 2){
         //console.log("CLICOU NA ABA DOCUMENTOS ")
         this.documentosService.listAll(this.pessoaId)
      }

      if(event.index == 3){
         //console.log("CLICOU NA ABA CONTATOS ")
         this.contatosService.listAll(this.pessoaId)
      }

      /*if(event.index == 4){
         //console.log("CLICOU NA ABA EMPRESAS ")
         this.empresasService.listAll(this.pessoaId);
      }*/
   }
}
