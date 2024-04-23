package com.financeiro.financeiroapi.DTO;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class CorrespondenciasDTO {
	
	private Long id;
	private String descricao;
	private OffsetDateTime dataRegistro;

}
