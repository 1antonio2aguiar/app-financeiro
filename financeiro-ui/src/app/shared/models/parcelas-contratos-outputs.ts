import { Contratos } from 'src/app/shared/models/contratos';

export default interface ParcelasContratosOutput {

   id?: number,
   parcela?: number,
   valor?: string,
   juros ?: string,
   multa ?: string,
   correcao ?: string,
   desconto ?: string,
   valorPago?: string,
   dataVencimento?: Date ,
   dataPagamento?: Date,
   documento?: string,
   bancoId?: number,
   observacao?: string,
   contratoId?: number,
   contratos?: Contratos,

}
