package com.financeiro.financeiroapi.input;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CorrespondenciasInput {
	
	@NotBlank
	private String descricao;
}
