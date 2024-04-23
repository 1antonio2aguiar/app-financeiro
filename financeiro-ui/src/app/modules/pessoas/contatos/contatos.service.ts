import { Injectable, Injector, EventEmitter } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { Contatos } from './../../../shared/models/contatos';
import ContatosOutput  from 'src/app/shared/models/contatos-output';

@Injectable({
  providedIn: 'root'
})

export class ContatosService extends BaseResourceService<Contatos>{

   // Contatos por pessoa
   private contatosEventHendler: EventEmitter<Contatos[]>;

   // contatos por id
   private contatosEventHendlerId: EventEmitter<Contatos>;

   header = new HttpHeaders(
      {
         'Content-Type': 'application/json'
   });


   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'contatos', injector, Contatos.fromJson);

      // Lista de contatos por pessoa
      this.contatosEventHendler = new EventEmitter<Contatos[]>();

      // Contatos por id
      this.contatosEventHendlerId = new EventEmitter<Contatos>();

   }


   // Busca lista de contatos por pessoa
   listAll(pessoa): Promise<any> {
      //console.log("ESTA NO SERVICE ", pessoa)
      return this.http.get<Contatos[]>(this.apiPath + '/findByPessoaId?pessoaId='+pessoa)
         .toPromise()
         .then(response => {
         this.contatosEventHendler.emit(response);
      });
   }

   contatosChangeSubscribe(callBack:(contatos: Contatos[]) => void){
      this.contatosEventHendler.subscribe(callBack);
   }

   // Busca contato por ID
   buscaContato(contatoId): Promise<any> {

      return this.http.get<Contatos>(this.apiPath + '/'+contatoId)
         .toPromise()
         .then(response => {
         this.contatosEventHendlerId.emit(response);
      });
   }

      contatosEditSubscribeId(callBack:(contatos: ContatosOutput) => void){
      this.contatosEventHendlerId.subscribe(callBack);
   }

   createContato(resource): Promise<any> {
      return this.http.post(this.apiPath+'', resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   updateContato(resource): Promise<any> {
      return this.http.put(this.apiPath + '/' + JSON.parse(resource).id, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   deleteContato(id): Promise<any>{
      return this.http.delete(this.apiPath + '/' + id)
      .toPromise()
      .then(response => response);
   }
}

