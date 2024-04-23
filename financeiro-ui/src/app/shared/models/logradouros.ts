import { BaseResourceModel } from "./base-resource.model";

import { TiposLogradouros } from './tipos-logradouros';
import { Cidades } from "./cidades";

export class Logradouros extends BaseResourceModel {

   constructor(
      public id?: number,
      public nome?: string,

      public cidadesId?: number,
      public cidades?: Cidades,

      public tiposLogradourosId?: number,
      public tiposLogradouros?: TiposLogradouros,

   ) {
      super();
   }

   static fromJson(jsonData: any): Logradouros {
      const logradouros = {
         ...jsonData,
         tiposLogradourosId: jsonData["tiposLogradouros"]["id"],
         cidadesId: jsonData["cidades"]["id"],
      };
      return Object.assign(new Logradouros(), logradouros);
   }

   static toJson(obj: any): Logradouros{
      delete obj['cidades'];
      return Object.assign(new Logradouros(), obj);
   }
}
