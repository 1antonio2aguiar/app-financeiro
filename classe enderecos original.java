package com.financeiro.financeiroapi.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.model.enums.StatusEndereco;
import com.financeiro.financeiroapi.validation.ValidationGroups;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "enderecos")
public class Enderecos {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.PessoaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;
	
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "bairro_id")
	private Bairros  bairro;
	
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "logradouro_id")
	private Logradouros logradouro;
	
	private Long numero;
	private String complemento;
	
	@NotNull
	private BigDecimal taxa;
	
	@Enumerated(EnumType.STRING)
	@JsonProperty(access = Access.READ_ONLY)
	private StatusEndereco status;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime cadastro;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime alteracao;
	
	@OneToMany(mappedBy = "enderecos", cascade = CascadeType.ALL)
	List<Correspondencias> correspondencias = new ArrayList<>();
 
	public Correspondencias adicionarCorrespondencia(String descricao) {
		
		Correspondencias correspondencia = new Correspondencias();
		correspondencia.setDescricao(descricao);
		correspondencia.setDataRegistro(OffsetDateTime.now());
		correspondencia.setEnderecos(this);
		
		this.getCorrespondencias().add(correspondencia);
		return correspondencia;
	}

	/*public void finalizar() {
		
		if(!StatusEndereco.ATIVO.equals(getStatus())) {
			throw new NegocioException("Status do endereço não pode se alterado.");
		}
		
		setStatus(StatusEndereco.SUSPENSO);
		setAlteracao(OffsetDateTime.now());
		
	}   */
	 

}
