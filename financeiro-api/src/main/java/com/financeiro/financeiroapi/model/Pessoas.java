package com.financeiro.financeiroapi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.financeiro.financeiroapi.model.enums.EstadoCivil;
import com.financeiro.financeiroapi.model.enums.FisicaJuridica;
import com.financeiro.financeiroapi.model.enums.Genero;
import com.financeiro.financeiroapi.model.enums.SituacaoEnum;
import com.financeiro.financeiroapi.validation.ValidationGroups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "pessoas")
public class Pessoas {
	
	@NotNull(groups = ValidationGroups.PessoaId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 80, min = 3)
	private String nome;
	
	private FisicaJuridica fisicaJuridica;
	private Genero genero; 			
	private EstadoCivil estadoCivil; 
	private SituacaoEnum situacao;
	private String cpfCnpj;
	private Date dataRegistro;
	private String observacao;
	private String nomeFantasia;
	private String objetoSocial;
	private Long tipoEmpresaId;
	private Long tipoPessoaId;
	
}
