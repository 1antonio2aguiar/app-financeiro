import { BaseResourceModel } from './base-resource.model';

export class Estados extends BaseResourceModel {
  constructor(
    public codigo_inep?: number,
    public id?: number,
    public nome?: string,
    public uf?: string,
  ) {
  super();
}

  static fromJson(jsonData: any): Estados {
      return Object.assign(new Estados(), jsonData);
  }
}
