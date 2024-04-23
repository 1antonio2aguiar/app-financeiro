import { Injectable, Injector } from '@angular/core';
import { Estados } from './../../shared/models/estados';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { EstadosFiltro } from './estados-filtro';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})

export class EstadosService extends BaseResourceService<Estados> {
  constructor(protected injector: Injector) {
    super(environment.apiUrl + 'estados', injector, Estados.fromJson);
  }

  pesquisar(filtro: EstadosFiltro): Promise<any> {
    let params = filtro.params;
    params = params
      .append('page', filtro.pagina.toString())
      .append('size', filtro.itensPorPagina.toString());
    return this.http
      .get<any>(this.apiPath, { params })
      .toPromise()
      .then((response) => {
        const estados = response.content;
        const resultado = {
          estados,
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
