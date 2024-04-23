import { BaseResourceModel } from './base-resource.model';

export class Enderecos extends BaseResourceModel {
  constructor(

    public id?: number,
    public pessoa?: number,
    public tipoEndereco?: number,
    public bairro?: number,
    public logradouro?: number,
    public cep?: number,
    public numero?: number,
    public complemento?: string,
    public usuario?: string,

  ) {
    super();
  }
  static fromJson(jsonData: any): Enderecos {
    return Object.assign(new Enderecos(), jsonData);
  }
}
