package com.financeiro.financeiroapi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.financeiro.financeiroapi.model.enums.IdentificacaoCep;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "ceps")
public class Ceps implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cep;
	private Long numeroIni;
	private Long numeroFin;
	private IdentificacaoCep identificacao;
	
	@ManyToOne
	@JoinColumn(name = "bairro_id")
	private Bairros  bairros;
	
	@ManyToOne
	@JoinColumn(name = "logradouro_id")
	private Logradouros logradouros;
}
