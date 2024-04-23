import { FormsModule } from '@angular/forms';
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

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EstadosRoutingModule } from './estados-routing.module';
import { EstadosCadastroComponent } from './estados-cadastro/estados-cadastro.component';
import { EstadosPesquisaComponent } from './estados-pesquisa/estados-pesquisa.component';


@NgModule({
  declarations: [EstadosCadastroComponent, EstadosPesquisaComponent],
  imports: [
    SharedModule,
    FormsModule,
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
    EstadosRoutingModule
  ]
})
export class EstadosModule { }
