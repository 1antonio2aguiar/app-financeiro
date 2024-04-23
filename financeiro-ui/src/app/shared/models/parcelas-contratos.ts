import { Contratos } from './contratos';
import { DecimalPipe } from '@angular/common';
import { BaseResourceModel } from './base-resource.model';

export class ParcelasContratos extends BaseResourceModel {

   constructor(
      public id?: number,
      public parcela?: number,
      public valor?: DecimalPipe,
      public juros ?: DecimalPipe,
      public multa ?: DecimalPipe,
      public correcao ?: DecimalPipe,
      public desconto ?: DecimalPipe,
      public valorPago ?: DecimalPipe,
      public dataVencimento?: Date ,
      public dataPagamento?: Date,
      public documento?: string,
      public bancoId?: number,
      public observacao?: string,

      public contratoId?: number,
      public contratos?: Contratos,
   ) {
     super();
   }

   static fromJson(jsonData: any): ParcelasContratos {
     const parcelasContratos = {
       ...jsonData,
       contratosId: jsonData["contratos"]["id"],
     };
     return Object.assign(new ParcelasContratos(), parcelasContratos);
   }

   static toJson(obj: any): ParcelasContratos{
     delete obj['contratos'];
     return Object.assign(new ParcelasContratos(), obj);
   }
}
