package com.financeiro.financeiroapi.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.financeiro.financeiroapi.DTO.CorrespondenciasDTO;
import com.financeiro.financeiroapi.DTO.EnderecosDTO;
import com.financeiro.financeiroapi.input.CidadesInput;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Correspondencias;
import com.financeiro.financeiroapi.model.Enderecos;

public class CidadesAssembler {
	
	@Autowired private ModelMapper modelMapper;
	
	public Cidades toEntity(CidadesInput cidadesInput) {
		return modelMapper.map(cidadesInput, Cidades.class);
	}
	
	
	public CidadesInput toModel(Cidades cidades) {
		return modelMapper.map(cidades, CidadesInput.class);
	}
	
	public List<CidadesInput> toCollectionInput(List<Cidades> cidades){
		return cidades.stream()
				.map(this:: toModel)
				.collect(Collectors.toList());
	}
	
	

}
