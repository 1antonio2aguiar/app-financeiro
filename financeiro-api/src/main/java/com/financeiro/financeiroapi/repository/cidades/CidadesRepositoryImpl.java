package com.financeiro.financeiroapi.repository.cidades;

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

import com.financeiro.financeiroapi.filter.CidadesFilter;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Cidades_;
import com.financeiro.financeiroapi.model.Estados_;

public class CidadesRepositoryImpl implements CidadesRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Cidades> filtrar(CidadesFilter cidadesFilter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Cidades> criteria = builder.createQuery(Cidades.class);
	    Root<Cidades> root = criteria.from(Cidades.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(cidadesFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Cidades> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(cidadesFilter));
	}
	
	private Predicate[] criarRestricoes(
			CidadesFilter cidadesFilter, CriteriaBuilder builder, Root<Cidades> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// CIDADE ID
		if(cidadesFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Cidades_.ID), cidadesFilter.getId()));
		}
		
		// NOME
	    if (StringUtils.hasLength(cidadesFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(Cidades_.NOME)),
	              "%" + cidadesFilter.getNome().toLowerCase() + "%"));
	    }
	    
	    // CIDADES POR ESTADO(UF)
	    if (StringUtils.hasLength(cidadesFilter.getEstadosFilter().getUf())) {
		      predicates.add(
		          builder.like(
		              builder.lower(root.get(Cidades_.ESTADOS).get(Estados_.UF)),
		              "%" + cidadesFilter.getEstadosFilter().getUf().toLowerCase() + "%"));
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
	
	private Long total(CidadesFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Cidades> root = criteria.from(Cidades.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
