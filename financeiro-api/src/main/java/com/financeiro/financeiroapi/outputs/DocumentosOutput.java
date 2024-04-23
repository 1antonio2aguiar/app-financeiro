package com.financeiro.financeiroapi.outputs;

import com.financeiro.financeiroapi.model.enums.TipoDocumento;

import lombok.Data;

@Data
public class DocumentosOutput {
	
	private Long id;
	private Long pessoa;
	private TipoDocumento tipoDocumento;
	private String numeroDocumento;
	private String observacao;
	private String dataDocumento;
	private String dataExpedicao;
	private String dataValidade;
	
}
