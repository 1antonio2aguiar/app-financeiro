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
import { DropdownModule } from 'primeng/dropdown';
import { DialogService } from 'primeng/dynamicdialog';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LogradourosRoutingModule } from './logradouros-routing.module';
import { LogradourosCadastroComponent } from './logradouros-cadastro/logradouros-cadastro.component';
import { LogradourosPesquisaComponent } from './logradouros-pesquisa/logradouros-pesquisa.component';
import { LogradourosModalComponent } from './logradouros-modal/logradouros-modal.component';

@NgModule({
   declarations: [LogradourosCadastroComponent, LogradourosPesquisaComponent, LogradourosModalComponent],
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
      LogradourosRoutingModule
   ],
   providers: [DialogService],
   exports:[LogradourosModalComponent],
})

export class LogradourosModule { }
