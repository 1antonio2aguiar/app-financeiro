package com.financeiro.financeiroapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "estados")
public class Estados implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 40, min = 4)
	private String nome;
	
	@NotBlank
	@Size(max = 2, min = 2)
	private String uf;
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
	
	public String getUf() {
		return uf == null ? null :uf.toUpperCase();
	}
	
	//@OneToMany(mappedBy = "estados", cascade = CascadeType.ALL)
	//List<Cidades> cidades = new ArrayList<>();
	
}
