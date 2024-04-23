import { BaseResourceModel } from './base-resource.model';

export class TiposPessoas extends BaseResourceModel {
  constructor(

    public descricao?: string,
    public id?: number,

  ) {

    super();

  }

  static fromJson(jsonData: any): TiposPessoas {
      return Object.assign(new TiposPessoas(), jsonData);
  }
}
