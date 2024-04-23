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
import { TabViewModule } from 'primeng/tabview';
import { SelectButtonModule} from 'primeng/selectbutton';
import { TextMaskModule } from 'angular2-text-mask';
import { FormsModule } from '@angular/forms';

import { ContratosRoutingModule } from './contratos-routing.module';
import { ContratosPesquisaComponent } from './contratos-pesquisa/contratos-pesquisa.component';
import { ContratosCadastroComponent } from './contratos-cadastro/contratos-cadastro.component';
import { ContratosCadastroViewComponent } from './contratos-cadastro-view/contratos-cadastro-view.component';
import { ParcelasContratosPesquisaComponent } from './parcelas-contratos/parcelas-contratos-pesquisa/parcelas-contratos-pesquisa.component';
import { ParcelasContratosCadastroComponent } from './parcelas-contratos/parcelas-contratos-cadastro/parcelas-contratos-cadastro.component';

@NgModule({
  declarations: [ContratosPesquisaComponent, ContratosCadastroComponent, ContratosCadastroViewComponent,
   ParcelasContratosPesquisaComponent, ParcelasContratosCadastroComponent],
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
   CommonModule,
   DropdownModule,
   SelectButtonModule,
   TabViewModule,
   FormsModule,
   ContratosRoutingModule
  ],
  providers: [
    DialogService
  ],
})

export class ContratosModule { }
