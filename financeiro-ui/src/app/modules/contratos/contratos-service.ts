import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from '../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { Contratos } from '../../shared/models/contratos';
import { ContratosFiltro } from './contratos-filtro';

@Injectable({
   providedIn: 'root'
})

export class ContratosService extends BaseResourceService<Contratos>{

   header = new HttpHeaders({
      'Content-Type': 'application/json'
   });

   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'contratos', injector, Contratos.fromJson);
   }

   pesquisar(filtro: ContratosFiltro): Promise<any> {
      let params = filtro.params;
      params = params
      .append('page', filtro.pagina.toString())
      .append('size', filtro.itensPorPagina.toString())

      return this.http.get<any>(
         this.apiPath,
           {params}
      )
      .toPromise()
      .then(response => {
         const contratos = response.content;
         const resultado = {
            contratos,
            total: response.totalElements
         };
         return resultado;
      });
   }

   listAll(): Promise<any> {
      return this.http.get<any>( this.apiPath + '/')
        .toPromise()
        .then(response => response.content
      );
   }

   createContrato(resource): Promise<any> {
      return this.http.post(this.apiPath+'/', resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   updateContrato(resource): Promise<any> {
      return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }
}
