import { BaseResourceModel } from './base-resource.model';

export class TiposContatos extends BaseResourceModel {
constructor(
public descricao?: string,
public id?: number,
) {
super();
}
static fromJson(jsonData: any): TiposContatos {
    return Object.assign(new TiposContatos(), jsonData);
}
}
