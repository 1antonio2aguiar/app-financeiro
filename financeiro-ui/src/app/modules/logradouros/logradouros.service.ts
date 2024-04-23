import { HttpHeaders } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';

import { LogradourosFiltro } from './logradouros-filtro';
import { Logradouros } from './../../shared/models/logradouros';

@Injectable({
   providedIn: 'root'
})

export class LogradourosService extends BaseResourceService<Logradouros> {

   header = new HttpHeaders({
      'Content-Type': 'application/json'
   });

 	constructor(protected injector: Injector) {
 	   super(environment.apiUrl + 'logradouros', injector, Logradouros.fromJson);
 	}

 	pesquisar(filtro: LogradourosFiltro): Promise<any> {
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
        const logradouros = response.content;
        const resultado = {
          logradouros,
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

   createLogradouro(resource): Promise<any> {
      return this.http.post(this.apiPath+'/completo', resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   updateLogradouro(resource): Promise<any> {
      return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }
}
