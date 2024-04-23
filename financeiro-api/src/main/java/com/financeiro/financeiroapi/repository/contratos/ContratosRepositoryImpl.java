package com.financeiro.financeiroapi.repository.contratos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import com.financeiro.financeiroapi.filter.ContratosFilter;
import com.financeiro.financeiroapi.model.Contratos;
import com.financeiro.financeiroapi.model.Contratos_;
import com.financeiro.financeiroapi.model.GruposFinanceiros_;
import com.financeiro.financeiroapi.model.Pessoas_;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros_;

public class ContratosRepositoryImpl implements ContratosRepositoryQuery{
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Contratos> filtrar(ContratosFilter contratosFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Contratos> criteria = builder.createQuery(Contratos.class);
	    Root<Contratos> root = criteria.from(Contratos.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(contratosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Contratos> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	    
	    return new PageImpl<>(query.getResultList(), pageable, total(contratosFilter));
	}
	
	private Predicate[] criarRestricoes(
		ContratosFilter contratosFilter, CriteriaBuilder builder, Root<Contratos> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(contratosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Contratos_.ID), contratosFilter.getId()));
		}
		
		// Número contrato
		if(contratosFilter.getNumeroContrato() != null) {
			predicates.add(builder.equal(root.get(Contratos_.NUMERO_CONTRATO), contratosFilter.getNumeroContrato()));
		}
		
		// Receita / Despesa
		if (StringUtils.hasLength(contratosFilter.getTiposGruposFinanceirosFilter().getGruposFinanceirosFilter().getReceitaDespesa())) {
			String receitaDespesa = contratosFilter.getTiposGruposFinanceirosFilter().getGruposFinanceirosFilter().getReceitaDespesa().toUpperCase();		
			
			String letra = receitaDespesa.substring(0, 1);
			System.err.println("Letra " + letra);
				if (letra.equals ("R")) {
					predicates.add(builder.equal(root
						.get(Contratos_.TIPOS_GRUPOS_FINANCEIROS)
						.get(TiposGruposFinanceiros_.GRUPOS_FINANCEIROS)
						.get(GruposFinanceiros_.RECEITA_DESPESA), 0));
				} else {
				if (letra.equals ("D")) {
					predicates.add(builder.equal(root
							.get(Contratos_.TIPOS_GRUPOS_FINANCEIROS)
							.get(TiposGruposFinanceiros_.GRUPOS_FINANCEIROS)
							.get(GruposFinanceiros_.RECEITA_DESPESA),1));
				}
			}
	    }
		
		// SITUAÇÃO
		if(contratosFilter.getSituacao() != null) {
			predicates.add(builder.equal(root.get(Contratos_.SITUACAO), contratosFilter.getSituacao()));
		}
		
				
		// NOME CONTRTADO
	    if (StringUtils.hasLength(contratosFilter.getContratadoFilter().getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(Contratos_.PESSOA_CONTRATADO).get(Pessoas_.NOME)),
	              "%" + contratosFilter.getContratadoFilter().getNome().toLowerCase() + "%"));
	    }
	    
	    // NOME CONTRATANTE
	    if (StringUtils.hasLength(contratosFilter.getContratanteFilter().getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(Contratos_.PESSOA_CONTRATANTE).get(Pessoas_.NOME)),
	              "%" + contratosFilter.getContratanteFilter().getNome().toLowerCase() + "%"));
	    }
	    
		// TIPOS GRUPOS FINANCEIROS DESCRICAO
		if (StringUtils.hasLength(contratosFilter.getTiposGruposFinanceirosFilter().getGruposFinanceirosFilter().getDescricao())) {
			predicates.add(
		      builder.like(
		          builder.lower(root
	        		.get(Contratos_.TIPOS_GRUPOS_FINANCEIROS)
					.get(TiposGruposFinanceiros_.GRUPOS_FINANCEIROS)
					.get(GruposFinanceiros_.DESCRICAO)),
	          "%" + contratosFilter.getTiposGruposFinanceirosFilter().getGruposFinanceirosFilter().getDescricao().toLowerCase() + "%"));
		}
	    
	    // GRUPOS FINANCEIROS DESCRICAO
	    if (StringUtils.hasLength(contratosFilter.getTiposGruposFinanceirosFilter().getDescricao())) {
	    	predicates.add(
	  	          builder.like(
	  	              builder.lower(root.get(Contratos_.TIPOS_GRUPOS_FINANCEIROS).get(GruposFinanceiros_.DESCRICAO)),
	  	              "%" + contratosFilter.getTiposGruposFinanceirosFilter().getDescricao().toLowerCase() + "%"));
	    }
	    
	    if (contratosFilter.getDataInicio() != null) {
    		Date data = contratosFilter.getDataInicio();
	    	predicates.add(builder.equal(root.get(Contratos_.DATA_INICIO), data));
	    }
	    
	    if (contratosFilter.getDataFim() != null) {
    		Date data = contratosFilter.getDataFim();
	    	predicates.add(builder.equal(root.get(Contratos_.DATA_FIM), data));
	    }
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
	    int paginaAtual = pageable.getPageNumber();
	    int totalRegistrosPorPagina = pageable.getPageSize();
	    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
	
	    query.setFirstResult(primeiroRegistroDaPagina);
	    query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(ContratosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Contratos> root = criteria.from(Contratos.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}	
}
