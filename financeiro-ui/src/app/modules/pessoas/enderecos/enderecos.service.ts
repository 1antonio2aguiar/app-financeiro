import { Injectable, Injector, EventEmitter } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { Enderecos } from './../../../shared/models/enderecos';
import EnderecosOutput  from 'src/app/shared/models/enderecos-output';

@Injectable({
   providedIn: 'root'
})

export class EnderecosService extends BaseResourceService<Enderecos>{

   private enderecosEventHendler: EventEmitter<Enderecos[]>;
   private enderecosEventHendlerId: EventEmitter<Enderecos>;

   header = new HttpHeaders(
      {
         'Content-Type': 'application/json'
   });

   constructor(protected injector: Injector) {
      super(environment.apiUrl + 'enderecos', injector, Enderecos.fromJson);
      // Lista de enderecos por pessoa
      this.enderecosEventHendler = new EventEmitter<Enderecos[]>();

      // Enderecos por id
      this.enderecosEventHendlerId = new EventEmitter<Enderecos>();
   }

   // Busca lista de enderecos por pessoa
   listAll(pessoa): Promise<any> {
      //console.log("ESTA NO SERVICE LIST ALL")
      return this.http.get<Enderecos[]>(environment.apiUrl + 'enderecos/findByPessoaId?pessoaId='+pessoa)
      .toPromise()
      .then(response => {
         this.enderecosEventHendler.emit(response);
      });
   }

   enderecosChangeSubscribe(callBack:(enderecos: Enderecos[]) => void){
      this.enderecosEventHendler.subscribe(callBack);
   }

   // Busca endereco por ID
   buscaEndereco(enderecoId): Promise<any> {
      return this.http.get<Enderecos>(this.apiPath + '/'+enderecoId)
      .toPromise()
      .then(response => {
         this.enderecosEventHendlerId.emit(response);
      });
   }

   buscaEnderecoId(enderecoId): Promise<any> {
      return this.http.get<Enderecos>(this.apiPath + '/'+enderecoId)
      .toPromise();
   }

   enderecosEditSubscribeId(callBack:(enderecos: EnderecosOutput) => void){
      this.enderecosEventHendlerId.subscribe(callBack);
   }

   createEndereco(resource): Promise<any> {
      return this.http.post(this.apiPath, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   updateEndereco(resource): Promise<any> {
      //console.log("ID : " + JSON.parse(resource).id);
      //console.log("Endereco: " + this.apiPath + '/' + JSON.parse(resource).id);
      return this.http.put(this.apiPath + '/' + JSON.parse(resource).id, resource, { headers: this.header })
      .toPromise()
      .then(response => response);
   }

   deleteEndereco(id): Promise<any>{
      //console.log("ID : " + id);
      return this.http.delete(this.apiPath + '/' + id)
      .toPromise()
      .then(response => response);
   }
}
