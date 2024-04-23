package com.financeiro.financeiroapi.input;

import java.math.BigDecimal;
import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.financeiro.financeiroapi.model.enums.SituacaoEnum;

import lombok.Data;

@Data
public class ContratosInput {
	
	private Long id;
	
	@NotNull
	private Integer situacao;
	
	@Valid
	@NotNull
	private Long contratanteId;
	
	@Valid
	@NotNull
	private Long contratadoId;
	
	@Valid
	@NotNull
	private Long tipoGrupoFinanceiroId;
	
	@NotNull
	private Date dataContrato;
	
	@NotNull
	private Date dataInicio;
	private Date dataFim;
	
	@Valid
	@NotNull
	private Long numeroContrato;
	
	@Valid
	@NotNull
	private BigDecimal  valorContrato;
	
	@Valid
	@NotNull
	private String usuario; 
	private String observacao; 

}
