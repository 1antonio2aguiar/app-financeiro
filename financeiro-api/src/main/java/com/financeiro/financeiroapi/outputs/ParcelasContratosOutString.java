package com.financeiro.financeiroapi.outputs;

import com.financeiro.financeiroapi.model.Contratos;

import lombok.Data;

@Data
public class ParcelasContratosOutString {
	private Long id ; 
	private Long contratoId ;
	private Long parcela ;
	private String valor ;
	private String juros ;
	private String multa ;
	private String correcao ;
	private String desconto ;
	private String valorPago ;
	private String dataVencimento ;
	private String dataPagamento ;
	private String documento ;
	private String observacao ; 
	private Long bancoId ;
	
	private Contratos contratos;
}
