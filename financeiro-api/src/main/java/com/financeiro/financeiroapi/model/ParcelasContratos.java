package com.financeiro.financeiroapi.model;

import java.io.Serializable;
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

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "parcelas_contratos")
public class ParcelasContratos implements Serializable{
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "contrato_id")
	private Contratos  contratos;
	
	private Long parcela;
	private BigDecimal valor ;
	private BigDecimal juros ;
	private BigDecimal multa ;
	private BigDecimal correcao ;
	private BigDecimal desconto ;
	private BigDecimal valorPago ;
	private Date dataVencimento ;
	private Date dataPagamento ;
	private String documento ;
	private Long bancoId ;
	private String observacao;
	
}
