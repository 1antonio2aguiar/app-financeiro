import { BaseResourceModel } from './base-resource.model';

export class Contatos extends BaseResourceModel {
  constructor(
    public id?: number,
    public pessoa?: number,
    public tipoContato?: number,
    public contato?: string,
    public usuario?: string,

  ) {
    super();
  }
  static fromJson(jsonData: any): Contatos {
    return Object.assign(new Contatos(), jsonData);
  }
}
