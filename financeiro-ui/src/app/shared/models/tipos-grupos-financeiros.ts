import { BaseResourceModel } from "./base-resource.model";

import { GruposFinanceiros } from "./grupos-financeiros";

export class TiposGruposFinanceiros extends BaseResourceModel {

   constructor(
      public id?: number,
      public descricao?: string,
      public grupoFinanceiroId?: number,

      public gruposFinanceiros?: GruposFinanceiros,

   ) {
      super();
   }

   static fromJson(jsonData: any): TiposGruposFinanceiros {
      const tiposGruposFinanceiros = {
         ...jsonData,
         grupoFinanceiroId: jsonData["gruposFinanceiros"]["id"],
      };
      return Object.assign(new TiposGruposFinanceiros(), tiposGruposFinanceiros);
   }

   static toJson(obj: any): TiposGruposFinanceiros{
      delete obj['gruposFinanceiros'];
      return Object.assign(new TiposGruposFinanceiros(), obj);
   }
}
