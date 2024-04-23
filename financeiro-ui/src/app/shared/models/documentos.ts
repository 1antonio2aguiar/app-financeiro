
import { BaseResourceModel } from './base-resource.model';

export class Documentos extends BaseResourceModel {
  constructor(
    public id?: number,
    public pessoa?: number,
    public tipoDocumento?: number,
    public numeroDocumento?: string,
    public dataDocumento?: Date,
    public dataExpedicao?: Date,
    public dataValidade?: Date,
    public observacao?: string,

  ) {
    super();
  }
  static fromJson(jsonData: any): Documentos {
    return Object.assign(new Documentos(), jsonData);
  }
}

