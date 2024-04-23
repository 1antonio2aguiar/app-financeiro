package com.financeiro.financeiroapi.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.financeiro.financeiroapi.model.enums.SituacaoEnum;
import com.financeiro.financeiroapi.validation.ValidationGroups;
import com.financeiro.financeiroapi.validation.ValidationGroups.PessoaId;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "contratos")
public class Contratos {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer situacao;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.PessoaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "contratante_id")
	private Pessoas pessoaContratante;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.PessoaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "contratado_id")
	private Pessoas pessoaContratado;
	
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "tipo_grupo_financeiro_id")
	private TiposGruposFinanceiros  tiposGruposFinanceiros;
	
	private Date dataContrato;
	private Date dataInicio;
	private Date dataFim;
	private Long numeroContrato;
	private BigDecimal  valorContrato;
	@NotBlank
	private String usuario; 
	private String observacao;
	
	public Contratos() {
	} 
	
	public Contratos(Long id, SituacaoEnum situacao, Pessoas pessoaContratante, Pessoas pessoaContratado,
			 TiposGruposFinanceiros tiposGruposFinanceiros, Date dataContrato, Date dataInicio, Date dataFim,
			Long numeroContrato, BigDecimal valorContrato, String usuario, String observacao) {
		super();
		this.id = id;
		this.situacao = situacao.getCod();
		this.pessoaContratante = pessoaContratante;
		this.pessoaContratado = pessoaContratado;
		this.tiposGruposFinanceiros = tiposGruposFinanceiros;
		this.dataContrato = dataContrato;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.numeroContrato = numeroContrato;
		this.valorContrato = valorContrato;
		this.usuario = usuario;
		this.observacao = observacao;
	}

}
