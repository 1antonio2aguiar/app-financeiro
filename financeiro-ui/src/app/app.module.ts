import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { CoreModule } from './core/core.module';
import { SidebarModule}  from 'primeng/sidebar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MenubarModule } from 'primeng/menubar';
import { SharedModule } from './shared/shared.module';

// Isso aqui é para formatar pipes - moedas
import { DEFAULT_CURRENCY_CODE, LOCALE_ID, } from '@angular/core';
import { FormsModule } from '@angular/forms';
import ptBr from '@angular/common/locales/pt';
import { registerLocaleData } from '@angular/common';
registerLocaleData(ptBr);

@NgModule({

  declarations: [
    AppComponent,
  ],
  imports: [
      CoreModule,
      SharedModule,
      BrowserAnimationsModule,
      SidebarModule,
      MenubarModule,
      BrowserModule,
      FormsModule ,
      AppRoutingModule
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'pt' }, { provide: DEFAULT_CURRENCY_CODE, useValue: 'BRL' },],
  bootstrap: [AppComponent]
})
export class AppModule { }
