import { HttpHeaders } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';

import { Bairros } from './../../shared/models/bairros';
import { BairrosFiltro } from './bairros-filtro';


@Injectable({
   providedIn: 'root'
})

export class BairrosService extends BaseResourceService<Bairros>{

   header = new HttpHeaders({
      'Content-Type': 'application/json'
   });

   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'bairros', injector, Bairros.fromJson);
   }

   pesquisar(filtro: BairrosFiltro): Promise<any> {
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
         const bairros = response.content;
         const resultado = {
         bairros,
         total: response.totalElements
         };
         //console.log("Reesul: ", resultado)
         return resultado;
      });
   }

   listAll(): Promise<any> {
      return this.http.get<any>( this.apiPath + '/' )
      .toPromise()
      .then(response => response.content);
   }

   createBairro(resource): Promise<any> {
     return this.http.post(this.apiPath+'/completo', resource, { headers: this.header })
     .toPromise()
     .then(response => response);
   }

   updateBairro(resource): Promise<any> {
     return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
     .toPromise()
     .then(response => response);
   }
}
