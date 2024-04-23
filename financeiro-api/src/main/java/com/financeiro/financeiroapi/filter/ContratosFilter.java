package com.financeiro.financeiroapi.filter;

import java.sql.Date;

import com.financeiro.financeiroapi.model.enums.SituacaoEnum;

import lombok.Data;

@Data
public class ContratosFilter {
	
	private Long id;
	private Integer situacao;
	private PessoasFilter contratanteFilter = new PessoasFilter();
	private PessoasFilter contratadoFilter = new PessoasFilter();
	private TiposGruposFinanceirosFilter tiposGruposFinanceirosFilter = new TiposGruposFinanceirosFilter();
	private Date dataInicio;
	private Date dataFim;
	private Long numeroContrato;

}
