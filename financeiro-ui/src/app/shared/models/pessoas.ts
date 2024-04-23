import { BaseResourceModel } from './base-resource.model';

export class Pessoas extends BaseResourceModel {
   constructor(
      public id?: number,
      public nome?: string,
      public fisicaJuridica?: string,
      public cpfCnpj?: string,
      public estadoCivil?: string,
      public genero?: string,
      public dataRegistro?: Date,
      public nomeFantasia?: string,
      public objetoSocial?: string,
      public observacao?: string,
      public situacao?: string,
      //public tipoPessoa?: number,

   ) {
         super();
   }

   static fromJson(jsonData: any): Pessoas {
      return Object.assign(new Pessoas(), jsonData);
   }
}
