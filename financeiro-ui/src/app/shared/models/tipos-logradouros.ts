import { BaseResourceModel } from './base-resource.model';

export class TiposLogradouros extends BaseResourceModel {
   constructor(
      public id?: number,
      public descricao?: string,
      public sigla?: string,
   ) {
      super();
   }

   static fromJson(jsonData: any): TiposLogradouros {
         return Object.assign(new TiposLogradouros(), jsonData);
   }
}
