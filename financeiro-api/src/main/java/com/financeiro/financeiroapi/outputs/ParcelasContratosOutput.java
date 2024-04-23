package com.financeiro.financeiroapi.outputs;

import java.math.BigDecimal;

import com.financeiro.financeiroapi.model.Contratos;

import lombok.Data;

@Data
public class ParcelasContratosOutput {
	
	private Long id ; 
	private Long contratoId ;
	private Long parcela ;
	private BigDecimal valor ;
	private BigDecimal juros ;
	private BigDecimal multa ;
	private BigDecimal correcao ;
	private BigDecimal desconto ;
	private BigDecimal valorPago ;
	private String dataVencimento ;
	private String dataPagamento ;
	private String documento ;
	private String observacao ; 
	private Long bancoId ;
	
	private Contratos contratos;
	
}
