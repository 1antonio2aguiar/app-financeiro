package com.financeiro.financeiroapi.input;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.financeiro.financeiroapi.model.enums.EstadoCivil;
import com.financeiro.financeiroapi.model.enums.FisicaJuridica;
import com.financeiro.financeiroapi.model.enums.Genero;
import com.financeiro.financeiroapi.model.enums.SituacaoEnum;

import lombok.Data;

@Data
public class PessoasInput {
	
	private Long id;
	
	@NotNull
	private String nome;
	
	private FisicaJuridica fisicaJuridica;
	private Genero genero; 			
	private EstadoCivil estadoCivil; 
	private SituacaoEnum situacao;
	
	@NotNull
	private String cpfCnpj;
	
	private Date dataRegistro;
	private String nomeFantasia;
	private String objetoSocial;
	private Long tipoEmpresa_id;
	private Long tipoPessoa;
	private String observacao;
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
	
	public String getNomeFantasia() {
		return nomeFantasia == null ? null :nomeFantasia.toUpperCase();
	}
	
	public String getObjetoSocial() {
		return nomeFantasia == null ? null :objetoSocial.toUpperCase();
	}
	
	public String getObservacao() {
		return observacao == null ? null :observacao.toUpperCase();
	}
}
