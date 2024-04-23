import { Injectable, Injector, EventEmitter } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { Documentos } from 'src/app/shared/models/documentos';
import DocumentosOutput from 'src/app/shared/models/documentos-output';

@Injectable({
  providedIn: 'root'
})

export class DocumentosService extends BaseResourceService<Documentos>{

  // Documentos por pessoa
  private documentosEventHendler: EventEmitter<Documentos[]>;
  private documentos: Documentos[];

  // documentos por id
  private documentosEventHendlerId: EventEmitter<Documentos>;
  //private documentosId: Documentos;

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector) {
    super(environment.apiUrl + 'documentos', injector, Documentos.fromJson);

    // Lista de documentos por pessoa
    this.documentosEventHendler = new EventEmitter<Documentos[]>();
    this.documentos = [];

    // documentos por id
    this.documentosEventHendlerId = new EventEmitter<Documentos>();

  }

  // Busca lista de documentos por pessoa
  listAll(pessoa): Promise<any> {
    console.log("ESTA NO SERVICE ", pessoa)
    return this.http.get<Documentos[]>(this.apiPath + '/findByPessoaId?pessoaId='+pessoa)
      .toPromise()
      .then(response => {
        this.documentosEventHendler.emit(response);
    });
  }

  documentosChangeSubscribe(callBack:(documentos: Documentos[]) => void){
    this.documentosEventHendler.subscribe(callBack);
  }

  // Busca documentos por ID
  buscaDocumento(documentoId): Promise<any> {

    return this.http.get<Documentos>(this.apiPath + '/'+documentoId)
      .toPromise()
      .then(response => {
        this.documentosEventHendlerId.emit(response);
    });
  }

  buscaDocumentoId(documentoId): Promise<any> {
    return this.http.get<Documentos>(this.apiPath + '/'+documentoId)
      .toPromise();
  }

  documentosEditSubscribeId(callBack:(documentos: DocumentosOutput) => void){
    this.documentosEventHendlerId.subscribe(callBack);
  }

   createDocumento(resource): Promise<any> {
      return this.http.post(this.apiPath, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

  updateDocumento(resource): Promise<any> {
    return this.http.put(this.apiPath + '/' + JSON.parse(resource).id, resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

  deleteDocumento(id): Promise<any>{
    return this.http.delete(this.apiPath + '/' + id)
    .toPromise()
    .then(response => response);
  }

}
