import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';

import { TiposLogradourosFiltro } from './tipos-logradouros-filtro';
import { TiposLogradouros } from './../../shared/models/tipos-logradouros';

@Injectable({
  providedIn: 'root'
})

export class TiposLogradourosService extends BaseResourceService<TiposLogradouros> {


   constructor(protected injector: Injector) {
     super(environment.apiUrl + 'tiposLogradouros', injector, TiposLogradouros.fromJson);
   }

   pesquisar(filtro: TiposLogradourosFiltro): Promise<any> {
     let params = filtro.params;
     params = params
       .append('page', filtro.pagina.toString())
       .append('size', filtro.itensPorPagina.toString());
     return this.http.get<any>(
       this.apiPath,
       { params }
     )
       .toPromise()
       .then(response => {
         const tiposLogradouros = response.content;
         const resultado = {
           tiposLogradouros,
           total: response.totalElements
         };
         return resultado;
       });
   }

   listAll(): Promise<any> {
     return this.http.get<any>(this.apiPath + '/list')
       .toPromise()
       .then(response => response);
   }

 }

