import { BaseResourceModel } from './base-resource.model';

export class Situacoes extends BaseResourceModel {
constructor(
    public descricao?: string,
    public id?: number,
  ) {
  super();
  }
    static fromJson(jsonData: any): Situacoes {
      return Object.assign(new Situacoes(), jsonData);
  }
}
