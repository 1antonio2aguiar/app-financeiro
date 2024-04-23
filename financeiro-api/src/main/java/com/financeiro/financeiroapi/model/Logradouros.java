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

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "logradouros")
public class Logradouros implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 80, min = 1)
	private String nome;
	@Size(max = 100)
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidades  cidades;
	
	@ManyToOne
	@JoinColumn(name = "tipo_logradouro_id")
	private TiposLogradouros  tiposLogradouros;

}
