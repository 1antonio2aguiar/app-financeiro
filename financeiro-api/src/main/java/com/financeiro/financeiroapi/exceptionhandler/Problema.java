package com.financeiro.financeiroapi.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Data
public class Problema {
	
	private Integer status;
	private LocalDateTime dataHora;
	private String titulo;
	List<Campo> campos;
	
	@Data
	@AllArgsConstructor
	@Getter
	public static class Campo {
		
		private String nome;
		private String mensagem;
		
	}

}
