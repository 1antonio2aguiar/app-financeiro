package com.financeiro.financeiroapi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.financeiro.financeiroapi.model.enums.TipoDocumento;
import com.financeiro.financeiroapi.model.enums.TipoEndereco;
import com.financeiro.financeiroapi.validation.ValidationGroups;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "documentos")
public class Documentos {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private TipoDocumento tipoDocumento;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.PessoaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private Pessoas pessoa;
	
	private String numeroDocumento;
	private String observacao;
	
	private Date dataDocumento; 
	private Date dataExpedicao; 
	private Date dataValidade;

}
