package com.financeiro.financeiroapi.repository.logradouros;

import java.util.ArrayList;
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

import com.financeiro.financeiroapi.filter.LogradourosFilter;
import com.financeiro.financeiroapi.model.Bairros_;
import com.financeiro.financeiroapi.model.Cidades_;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.model.Logradouros_;


public class LogradourosRepositoryImpl implements LogradourosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Logradouros> filtrar(LogradourosFilter logradourosFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Logradouros> criteria = builder.createQuery(Logradouros.class);
	    Root<Logradouros> root = criteria.from(Logradouros.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(logradourosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Logradouros> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(logradourosFilter));
		
	}
	
	private Predicate[] criarRestricoes(
			LogradourosFilter logradourosFilter, CriteriaBuilder builder, Root<Logradouros> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(logradourosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Logradouros_.ID), logradourosFilter.getId()));
		}
		
		// NOME
	    if (StringUtils.hasLength(logradourosFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(Logradouros_.NOME)),
	              "%" + logradourosFilter.getNome().toLowerCase() + "%"));
	    }
	    
	    // LOGRADOUROS POR NOME DA CIDADES
		if (StringUtils.hasLength(
		        logradourosFilter.getCidadesFilter().getNome())) {
		        predicates.add(
		          builder.like(
		              builder.lower(
		                  root.get(Bairros_.CIDADES).get(Cidades_.NOME)),
		              "%"
		                  + logradourosFilter
		                      .getCidadesFilter()
		                      .getNome()
		                      .toLowerCase()
		                  + "%"));
		}
		
		// BAIRROS POR ID DO LOGRADOURO
	    if(logradourosFilter.getCidadesFilter().getId() != null) {
			predicates.add(builder.equal(root.get(Logradouros_.CIDADES).get(Cidades_.ID), logradourosFilter.getCidadesFilter().getId()));
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
	
	private Long total(LogradourosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Logradouros> root = criteria.from(Logradouros.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}

