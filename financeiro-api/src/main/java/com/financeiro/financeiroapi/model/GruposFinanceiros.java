package com.financeiro.financeiroapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.financeiro.financeiroapi.model.enums.ReceitaDespesaEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "grupos_financeiros")
public class GruposFinanceiros implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private ReceitaDespesaEnum receitaDespesa;
	
	@NotBlank
	@Size(max = 100, min = 4)
	private String descricao;

}
