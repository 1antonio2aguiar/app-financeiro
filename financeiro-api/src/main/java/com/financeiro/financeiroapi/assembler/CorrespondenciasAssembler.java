package com.financeiro.financeiroapi.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financeiro.financeiroapi.DTO.CorrespondenciasDTO;
import com.financeiro.financeiroapi.model.Correspondencias;

@Component
public class CorrespondenciasAssembler {
	
	@Autowired ModelMapper modelMapper;
	
	public CorrespondenciasDTO toModel(Correspondencias correspondencias) {
		return modelMapper.map(correspondencias, CorrespondenciasDTO.class);
	}
	
	public List<CorrespondenciasDTO> toCollectionDTO(List<Correspondencias> correspondencias){
		return correspondencias.stream()
				.map(this:: toModel)
				.collect(Collectors.toList());
	}
  
} 
