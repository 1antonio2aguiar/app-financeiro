package com.financeiro.financeiroapi.repository.tiposLogradouros;

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

import com.financeiro.financeiroapi.filter.TiposLogradourosFilter;
import com.financeiro.financeiroapi.model.TiposLogradouros;
import com.financeiro.financeiroapi.model.TiposLogradouros_;

public class TiposLogradourosRepositoryImpl implements TiposLogradourosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<TiposLogradouros> filtrar(TiposLogradourosFilter tiposLogradourosFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<TiposLogradouros> criteria = builder.createQuery(TiposLogradouros.class);
	    Root<TiposLogradouros> root = criteria.from(TiposLogradouros.class);
	 
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(tiposLogradourosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<TiposLogradouros> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(tiposLogradourosFilter));
		
	}
	
	//Aqui da lista sem paginacao
	@Override
	public List<TiposLogradouros> filtrar(TiposLogradourosFilter tiposLogradourosFilter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<TiposLogradouros> criteria = builder.createQuery(TiposLogradouros.class);
	    Root<TiposLogradouros> root = criteria.from(TiposLogradouros.class);
	
	    Predicate[] predicates = criarRestricoes(tiposLogradourosFilter, builder, root);
	    criteria.where(predicates);
	
	    TypedQuery<TiposLogradouros> query = manager.createQuery(criteria);
	    return query.getResultList();
	} 
	
	private Predicate[] criarRestricoes(
			TiposLogradourosFilter tiposLogradourosFilter, CriteriaBuilder builder, Root<TiposLogradouros> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(tiposLogradourosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(TiposLogradouros_.ID), tiposLogradourosFilter.getId()));
		}
		
		// DESCRICAO
	    if (StringUtils.hasLength(tiposLogradourosFilter.getDescricao())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(TiposLogradouros_.DESCRICAO)),
	              "%" + tiposLogradourosFilter.getDescricao().toLowerCase() + "%"));
	    }
	    
	    // DESCRICAO
	    if (StringUtils.hasLength(tiposLogradourosFilter.getSigla())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(TiposLogradouros_.SIGLA)),
	              "%" + tiposLogradourosFilter.getSigla().toLowerCase() + "%"));
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
	
	private Long total(TiposLogradourosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<TiposLogradouros> root = criteria.from(TiposLogradouros.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	} 

}
