package com.financeiro.financeiroapi.model.enums;

public enum SituacaoEnum {
	
	ATIVO(1, "ATIVO"),
	INATIVO(2, "INATIVO"),
	PARALISADO(3, "PARALISADO"),
	SUSPENSO(4, "SUSPENSO"),
	QUITADO(5, "QUITADO");
	
	private int cod;
	private String descricao;
	
	private SituacaoEnum(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	// static pode chamar se que o obj seja instanciado.
	public static SituacaoEnum toSituacaoEnum(Integer cod) {
		if(cod == null) return null;
		
		for (SituacaoEnum se : SituacaoEnum.values()) {
			if(cod.equals(se.getCod())) {
				return se;
			}
		}
		throw new IllegalArgumentException("Situação do contrato inválida " + cod);
	}
}
