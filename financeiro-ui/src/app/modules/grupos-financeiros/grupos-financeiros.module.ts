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
import { SelectButtonModule } from 'primeng/selectbutton';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GruposFinanceirosRoutingModule } from './grupos-financeiros-routing.module';
import { GruposFinanceirosCadastroComponent } from './grupos-financeiros-cadastro/grupos-financeiros-cadastro.component';
import { GruposFinanceirosPesquisaComponent } from './grupos-financeiros-pesquisa/grupos-financeiros-pesquisa.component';
import { GruposFinanceirosModalComponent } from './grupos-financeiros-modal/grupos-financeiros-modal.component';

@NgModule({
   declarations: [GruposFinanceirosCadastroComponent, GruposFinanceirosPesquisaComponent, GruposFinanceirosModalComponent],
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
      SelectButtonModule,
      GruposFinanceirosRoutingModule
   ]
})

export class GruposFinanceirosModule { }
