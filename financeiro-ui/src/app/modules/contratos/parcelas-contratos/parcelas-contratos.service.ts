import { Injectable, Injector, EventEmitter } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { ParcelasContratos } from 'src/app/shared/models/parcelas-contratos';
import { ParcelasContratosFiltro } from './parcelas-contratos-filtro';
import  ParcelasContratosOutput from 'src/app/shared/models/parcelas-contratos-outputs';

@Injectable({
   providedIn: 'root'
})

export class ParcelasContratosService extends BaseResourceService<ParcelasContratos>{

   // Parcelas por contrato
   private parcelasContratosEventHendler: EventEmitter<any>;

   // parcelas por id
   private parcelasContratosEventHendlerId: EventEmitter<ParcelasContratos>;

   header = new HttpHeaders({ 'Content-Type': 'application/json' });


   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'parcelasContratos', injector, ParcelasContratos.fromJson);

      // Lista de parcelas por contrato
      this.parcelasContratosEventHendler = new EventEmitter<any>();

      // Contratos por id
      this.parcelasContratosEventHendlerId = new EventEmitter<ParcelasContratos>();

   }

   listAllNoPaginacao(contrato): Promise<any> {
      return this.http.get<ParcelasContratos[]>(this.apiPath + '/findByContratosId?contratoId='+contrato)
      .toPromise()
      .then(response => {
         //console.log("Result ", response)
         this.parcelasContratosEventHendler.emit(response.sort(function(a,b) {
            return a.parcela < b.parcela ? -1 : a.parcela > b.parcela ? 1 : 0;
         }))
      });
   }

   listAllYesPaginacao(filtro: ParcelasContratosFiltro): Promise<any> {
      let params = filtro.params;
      //var paramContrato = params['updates'][0]['value'];

      params = params
      .append('page', filtro.pagina.toString())
      .append('size', filtro.itensPorPagina.toString())

      return this.http.get<any>( this.apiPath + '/',{params})
      .toPromise()
      .then(response => {
         this.parcelasContratosEventHendler.emit(response)
      });
   }

   pesquisar(filtro: ParcelasContratosFiltro): Promise<any> {
      let params = filtro.params;
      params = params
               .append('page', filtro.pagina.toString())
               .append('size', filtro.itensPorPagina.toString());
      return this.http.get<any>(this.apiPath + '/',{params})
      .toPromise()
      .then(response => {
         return response;
      });
   }

   parcelasContratosChangeSubscribe(callBack:(parcelasContratos: ParcelasContratos[]) => void){
      this.parcelasContratosEventHendler.subscribe(callBack);
   }

   // Busca parcela por ID
   buscaParcelaContrato(contratoId): Promise<any> {

      return this.http.get<ParcelasContratos>(this.apiPath + '/'+contratoId)
         .toPromise()
         .then(response => {
         this.parcelasContratosEventHendlerId.emit(response);
      });
   }

   // Busca a proxima parcela a ser cadastrada
   buscaMaxParcelaContrato(contrato): Promise<any> {
      return this.http.get<ParcelasContratos>(this.apiPath + '/findMaxParcela?contratoId='+contrato)
         .toPromise()
         .then(response => {
         this.parcelasContratosEventHendlerId.emit(response);
      });
   }

   parcelasContratosEditSubscribeId(callBack:(parcelasContatos: ParcelasContratosOutput) => void){
      this.parcelasContratosEventHendlerId.subscribe(callBack);
   }

   createParcelaContrato(resource): Promise<any> {
      console.log("Criando parcela")
      return this.http.post(this.apiPath+'', resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   updateParcelaContrato(resource): Promise<any> {
      return this.http.put(this.apiPath + '/' + JSON.parse(resource).id, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   deleteParcelaContrato(id): Promise<any>{
      return this.http.delete(this.apiPath + '/' + id)
      .toPromise()
      .then(response => response);
   }
}
