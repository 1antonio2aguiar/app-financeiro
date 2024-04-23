import { BaseResourceModel } from "./base-resource.model";

import { Bairros } from './bairros';
import { Logradouros } from './logradouros'

export class Ceps extends BaseResourceModel {

   constructor(

      public id?: number,
      public cep?: number,
      public numeroIni?: number,
      public numeroFin?: number,
      public identificacao?: number,

      public bairrosId?: number,
      public bairros?: Bairros,

      public logradourosId?: number,
      public logradouros?: Logradouros,

  ) {
    super();
  }

   static fromJson(jsonData: any): Ceps {
      const ceps = {
         ...jsonData,
         bairrosId: jsonData["bairros"]["id"],
         logradourosId: jsonData["logradouros"]["id"]
      };
      return Object.assign(new Ceps(), ceps);
   }

   static toJson(obj: any): Ceps{
      return Object.assign(new Ceps(), obj);
   }

}
