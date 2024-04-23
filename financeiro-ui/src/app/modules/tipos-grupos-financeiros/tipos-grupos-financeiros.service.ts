import { HttpHeaders } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';

import { TiposGruposFinanceiros } from 'src/app/shared/models/tipos-grupos-financeiros';
import { TiposGruposFinanceirosFiltro } from './tipos-grupos-financeiros-filtro';

@Injectable({
   providedIn: 'root'
})

export class TiposGruposFinanceirosService extends BaseResourceService<TiposGruposFinanceiros>{
   header = new HttpHeaders({
      'Content-Type': 'application/json'
   });

   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'tiposGruposFinanceiros', injector, TiposGruposFinanceiros.fromJson);
   }

   pesquisar(filtro: TiposGruposFinanceirosFiltro): Promise<any> {
      let params = filtro.params;
      params = params
               .append('page', filtro.pagina.toString())
               .append('size', filtro.itensPorPagina.toString());
      return this.http.get<any>(
         this.apiPath,
            {params}
      )
      .toPromise()
      .then(response => {
         const tiposGruposFinanceiros = response.content;
         const resultado = {
            tiposGruposFinanceiros,
         total: response.totalElements
         };
         return resultado;
      });
   }

   listAll(): Promise<any> {
      return this.http.get<any>( this.apiPath + '/' )
      .toPromise()
      .then(response => response.content);
   }

   createTiposGruposFinanceiros(resource): Promise<any> {
     return this.http.post(this.apiPath+'/', resource, { headers: this.header })
     .toPromise()
     .then(response => response);
   }

   updateTiposGruposFinanceiros(resource): Promise<any> {
     return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
     .toPromise()
     .then(response => response);
   }
}
