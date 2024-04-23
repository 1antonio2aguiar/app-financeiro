import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { Pessoas } from 'src/app/shared/models/pessoas';
import { PessoasFiltro } from './pessoas-filtro';

@Injectable({
   providedIn: 'root'
})

export class PessoasService extends BaseResourceService<Pessoas>{

   header = new HttpHeaders(
   {
      'Content-Type': 'application/json'
   });

   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'pessoas', injector, Pessoas.fromJson);
   }

   pesquisar(filtro: PessoasFiltro): Promise<any> {
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
         const pessoas = response.content;
         const resultado = {
            pessoas,
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

   createPessoa(resource): Promise<any> {
      return this.http.post(this.apiPath+'/', resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

    updatePessoa(resource): Promise<any> {
      return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }
}

