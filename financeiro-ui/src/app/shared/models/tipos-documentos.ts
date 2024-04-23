import { BaseResourceModel } from './base-resource.model';

export class TiposDocumentos extends BaseResourceModel {
  constructor(
  public descricao?: string,
  public id?: number,
  ) {
  super();
  }
  static fromJson(jsonData: any): TiposDocumentos {
      return Object.assign(new TiposDocumentos(), jsonData);
  }
}
