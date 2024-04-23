import { BaseResourceModel } from "./base-resource.model";

import { Cidades } from "./cidades";

export class Bairros extends BaseResourceModel {

  constructor(
    public id?: number,
    public nome?: string,

    public ciddaesId?: number,
    public cidades?: Cidades,

  ) {
    super();
  }

  static fromJson(jsonData: any): Bairros {
    const bairros = {
      ...jsonData,
      cidadesId: jsonData["cidades"]["id"],
    };
    return Object.assign(new Bairros(), bairros);
  }

  static toJson(obj: any): Bairros{
    delete obj['cidades'];
    return Object.assign(new Bairros(), obj);
  }

}
