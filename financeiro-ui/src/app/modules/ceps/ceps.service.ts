import { HttpHeaders } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';

import { Ceps } from 'src/app/shared/models/ceps';
import { CepsFiltro } from './ceps-filtro';

@Injectable({
   providedIn: 'root'
})

export class CepsService extends BaseResourceService<Ceps>{

   header = new HttpHeaders({
      'Content-Type': 'application/json'
   });

 	constructor(protected injector: Injector) {
 	   super(environment.apiUrl + 'ceps', injector, Ceps.fromJson);
 	}

   pesquisar(filtro: CepsFiltro): Promise<any> {
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
         const ceps = response.content;
         const resultado = {
         ceps,
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

   createCep(resource): Promise<any> {
     return this.http.post(this.apiPath, resource, { headers: this.header })
     .toPromise()
     .then(response => response);
   }

   updateCep(resource): Promise<any> {
     return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
     .toPromise()
     .then(response => response);
   }
}
