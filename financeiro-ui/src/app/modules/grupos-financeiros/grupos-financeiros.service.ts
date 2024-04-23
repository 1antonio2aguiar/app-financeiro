import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';

import { GruposFinanceirosFiltro } from './grupos-financeiros-filtro';
import { GruposFinanceiros } from './../../shared/models/grupos-financeiros';

@Injectable({
   providedIn: 'root',
})

export class GruposFinanceirosService extends BaseResourceService<GruposFinanceiros> {
   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'gruposFinanceiros', injector, GruposFinanceiros.fromJson);
   }

   pesquisar(filtro: GruposFinanceirosFiltro): Promise<any> {
      let params = filtro.params;
      params = params
      .append('page', filtro.pagina.toString())
      .append('size', filtro.itensPorPagina.toString());
      return this.http
      .get<any>(this.apiPath, { params })
      .toPromise()
      .then((response) => {
         const gruposFinanceiros = response.content;
         const resultado = {
            gruposFinanceiros,
            total: response.totalElements,
         };
         return resultado;
      });
   }

   listAll(): Promise<any> {
      return this.http
         .get<any>(this.apiPath + '/list')
         .toPromise()
         .then((response) => response);
   }
}
