package com.financeiro.financeiroapi.outputs;

import com.financeiro.financeiroapi.model.enums.EstadoCivil;
import com.financeiro.financeiroapi.model.enums.FisicaJuridica;
import com.financeiro.financeiroapi.model.enums.Genero;
import com.financeiro.financeiroapi.model.enums.SituacaoEnum;

import lombok.Data;

@Data
public class PessoasOutput {
	
	private Long id;	
	private String nome;	
	private FisicaJuridica fisicaJuridica;
	private Genero genero; 			
	private EstadoCivil estadoCivil; 
	private SituacaoEnum situacao;
	private String cpfCnpj;	
	private String dataRegistro;
	private String nomeFantasia;
	private String objetoSocial;
	private Long tipoEmpresa_id;
	private Long tipoPessoa;
	private String observacao;

}
