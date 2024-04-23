import { BaseResourceModel } from './base-resource.model';

export class TiposEmpresas extends BaseResourceModel {
  constructor(

    public id?: number,
    public descricao?: string,
    public usuario?: string,
  ) {
  super();
  }
  static fromJson(jsonData: any): TiposEmpresas {
      return Object.assign(new TiposEmpresas(), jsonData);
  }
}
