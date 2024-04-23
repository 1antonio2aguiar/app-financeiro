import { Injectable, Injector } from "@angular/core";
import { BaseResourceService } from "./../../shared/services/base-resource.service";
import { environment } from "src/environments/environment";

import { CidadesFiltro } from "./cidades-filtro";
import { Cidades } from "./../../shared/models/cidades";

@Injectable({
   providedIn: "root",
})

export class CidadesService extends BaseResourceService<Cidades> {
   constructor(protected injector: Injector) {
      super(environment.apiUrl + "cidades", injector, Cidades.fromJson);
   }

   pesquisar(filtro: CidadesFiltro): Promise<any> {
      let params = filtro.params;
      params = params
         .append("page", filtro.pagina.toString())
         .append("size", filtro.itensPorPagina.toString());
      return this.http
         .get<any>(this.apiPath, { params })
         .toPromise()
         .then((response) => {
            const cidades = response.content;
            const resultado = {
               cidades,
               total: response.totalElements,
            };
            return resultado;
      });
   }

   listAll(): Promise<any> {
      return this.http
         .get<any>(this.apiPath + "/list")
         .toPromise()
         .then((response) => response);
   }
}
