package com.financeiro.financeiroapi.outputs;

import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros;
import com.financeiro.financeiroapi.model.enums.SituacaoEnum;

import lombok.Data;

@Data
public class ContratosOutput {
	
	private Long    id;
	private Integer  situacao;
	private Long 	contratanteId;
	private Long 	contratadoId;
	private Long 	tipoGrupoFinanceiroId;
	private String 	dataContrato;
	private String 	dataInicio;
	private String 	dataFim;
	private Long 	numeroContrato;
	private String  valorContrato;
	private String 	usuario; 
	private String 	observacao;
	
	private TiposGruposFinanceiros tiposGruposFinanceiros;
	private Pessoas pessoasContratante;
	private Pessoas pessoasContratado;

}
