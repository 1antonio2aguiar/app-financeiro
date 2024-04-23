import { BaseResourceModel } from './base-resource.model';

export class GruposFinanceiros extends BaseResourceModel {
      constructor(
         public id?: number,
         public receitaDespesa?: number,
         public descricao?: string,
      ) {
      super();
   }

   static fromJson(jsonData: any): GruposFinanceiros {
      return Object.assign(new GruposFinanceiros(), jsonData);
   }
}
