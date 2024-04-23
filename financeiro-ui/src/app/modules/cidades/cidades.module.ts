import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from './../../shared/shared.module';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { IMaskModule } from 'angular-imask';
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { KeyFilterModule } from 'primeng/keyfilter';
import { TableModule } from 'primeng/table';
import { PanelModule } from 'primeng/panel';
import {DropdownModule} from 'primeng/dropdown';

import { CidadesRoutingModule } from './cidades-routing.module';
import { CidadesCadastroComponent } from './cidades-cadastro/cidades-cadastro.component';
import { CidadesPesquisaComponent } from './cidades-pesquisa/cidades-pesquisa.component';
import { CidadesModalComponent } from './cidades-modal/cidades-modal.component';

@NgModule({
declarations: [CidadesCadastroComponent, CidadesPesquisaComponent, CidadesModalComponent],
   imports: [
      SharedModule,
      IMaskModule,
      CalendarModule,
      CardModule,
      InputTextModule,
      KeyFilterModule,
      TableModule,
      PanelModule,
      MessagesModule,
      MessageModule,
      ToastModule,
      DropdownModule,
      CommonModule,
      CidadesRoutingModule
   ],
   exports:[CidadesModalComponent]
})

export class CidadesModule { }
