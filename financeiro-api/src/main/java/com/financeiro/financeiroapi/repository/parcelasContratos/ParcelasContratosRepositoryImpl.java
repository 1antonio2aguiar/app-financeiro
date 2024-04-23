package com.financeiro.financeiroapi.repository.parcelasContratos;

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

import com.financeiro.financeiroapi.filter.ParcelasContratosFilter;
import com.financeiro.financeiroapi.model.Contratos_;
import com.financeiro.financeiroapi.model.ParcelasContratos;
import com.financeiro.financeiroapi.model.ParcelasContratos_;

public class ParcelasContratosRepositoryImpl implements ParcelasContratosRepositoryQuery{
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<ParcelasContratos> filtrar(ParcelasContratosFilter parcelasContratosFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<ParcelasContratos> criteria = builder.createQuery(ParcelasContratos.class);
	    Root<ParcelasContratos> root = criteria.from(ParcelasContratos.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(parcelasContratosFilter, builder, root);
	    //Ordena por parcela
	    criteria.where(predicates).orderBy(builder.asc(root.get(ParcelasContratos_.PARCELA)));
	
	    TypedQuery<ParcelasContratos> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(parcelasContratosFilter));
		
	}
	
	private Predicate[] criarRestricoes(
			ParcelasContratosFilter parcelasContratosFilter, CriteriaBuilder builder, Root<ParcelasContratos> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID DA PARCELA
		if(parcelasContratosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(ParcelasContratos_.ID), parcelasContratosFilter.getId()));
		}
		
		// ID DO CONTRATO
		if(parcelasContratosFilter.getContratoId() != null) {
			predicates.add(builder.equal(root.get(ParcelasContratos_.CONTRATOS).get(Contratos_.ID), 
					parcelasContratosFilter.getContratoId()));
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
	
	private Long total(ParcelasContratosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<ParcelasContratos> root = criteria.from(ParcelasContratos.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}	

}
