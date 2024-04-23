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
import { DropdownModule } from 'primeng/dropdown';
import { DialogService } from 'primeng/dynamicdialog';

import { TiposGruposFinanceirosRoutingModule } from './tipos-grupos-financeiros-routing.module';
import { TiposGruposFinanceirosCadastroComponent } from './tipos-grupos-financeiros-cadastro/tipos-grupos-financeiros-cadastro.component';
import { TiposGruposFinanceirosPesquisaComponent } from './tipos-grupos-financeiros-pesquisa/tipos-grupos-financeiros-pesquisa.component';
import { TiposGruposFinanceirosModalComponent } from './tipos-grupos-financeiros-modal/tipos-grupos-financeiros-modal.component';
import { PessoasModalComponent } from './pessoas-modal/pessoas-modal.component';

@NgModule({
   declarations: [TiposGruposFinanceirosCadastroComponent, TiposGruposFinanceirosPesquisaComponent,
      TiposGruposFinanceirosModalComponent,
      PessoasModalComponent],
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
      TiposGruposFinanceirosRoutingModule
   ],
   providers: [DialogService]
})

export class TiposGruposFinanceirosModule { }
