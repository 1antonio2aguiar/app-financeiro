package com.financeiro.financeiroapi.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financeiro.financeiroapi.DTO.EnderecosDTO;
import com.financeiro.financeiroapi.input.EnderecosInput;
import com.financeiro.financeiroapi.model.Enderecos;

@Component
public class EnderecosAssembler {
	
	@Autowired private ModelMapper modelMapper;
	
	public EnderecosDTO toModel(Enderecos enderecos) {
		return modelMapper.map(enderecos, EnderecosDTO.class);
	}
	
	public List<EnderecosDTO> toCollectionEndereco(List<Enderecos> enderecos) {
		
		return enderecos.stream()
				.map(this::toModel)
				.collect(Collectors.toList()); 
	}
	
	public Enderecos toEntity(EnderecosInput enderecosInput) {
		return modelMapper.map(enderecosInput, Enderecos.class);
	}
 
}
