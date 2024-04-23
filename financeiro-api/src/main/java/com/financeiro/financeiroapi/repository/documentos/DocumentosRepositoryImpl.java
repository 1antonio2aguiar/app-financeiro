package com.financeiro.financeiroapi.repository.documentos;

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

import com.financeiro.financeiroapi.filter.DocumentosFilter;
import com.financeiro.financeiroapi.model.Documentos;
import com.financeiro.financeiroapi.model.Documentos_;

public class DocumentosRepositoryImpl implements DocumentosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Documentos> filtrar(DocumentosFilter documentosFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Documentos> criteria = builder.createQuery(Documentos.class);
	    Root<Documentos> root = criteria.from(Documentos.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(documentosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Documentos> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(documentosFilter));
		
	}
	
	private Predicate[] criarRestricoes(
			DocumentosFilter documentosFilter, CriteriaBuilder builder, Root<Documentos> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(documentosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Documentos_.ID), documentosFilter.getId()));
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
	
	private Long total(DocumentosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Documentos> root = criteria.from(Documentos.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
