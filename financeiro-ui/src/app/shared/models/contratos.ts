import { TiposGruposFinanceiros } from './tipos-grupos-financeiros';
import { Pessoas } from './pessoas';
import { DecimalPipe } from '@angular/common';
import { BaseResourceModel } from './base-resource.model';

export class Contratos extends BaseResourceModel {
   constructor(
      public id?: number,
      public dataContrato?: Date,
      public dataInicio?: Date,
      public dataFim?: Date,
      public numeroContrato?: number,
      public situacao?: number,
      public usuario?:  string,
      public observacao?: string,
      public valorContrato?: DecimalPipe,

      public pessoasContratanteId?: number,
      public pessoasContratante?: Pessoas,

      public pessoasContratadoId?:  number,
      public pessoasContratado?: Pessoas,

      public tipoGrupoFinanceiroId?: number,
      public tiposGruposFinanceiros?: TiposGruposFinanceiros,
   ) {
      super();
   }
   static fromJson(jsonData: any): Contratos {
      const contratos = {
        ...jsonData,
        pessoasContratanteId: jsonData["pessoasContratante"]["id"],
        pessoasContratadoId:  jsonData["pessoasContratado"]["id"],
        tipoGrupoFinanceiroId: jsonData["tiposGruposFinanceiros"]["id"]
      };
      return Object.assign(new Contratos(), contratos);
   }

   static toJson(obj: any): Contratos{
      return Object.assign(new Contratos(), obj);
   }

}
