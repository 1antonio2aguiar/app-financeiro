import { Logradouros } from 'src/app/shared/models/logradouros';
import { Bairros } from './bairros';
import { Ceps } from './ceps';

export default interface EnderecosOutput {

  id?: number,
  tipoEndereco?: string,
  cep?: number,
  numero?: number,
  complemento?: string,
  usuario?: string,

  bairros: Bairros,
  logradouros: Logradouros,
  ceps: Ceps,
}

