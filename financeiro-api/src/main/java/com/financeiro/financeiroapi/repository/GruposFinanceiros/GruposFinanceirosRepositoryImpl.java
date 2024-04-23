package com.financeiro.financeiroapi.repository.GruposFinanceiros;

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

import com.financeiro.financeiroapi.filter.GruposFinanceirosFilter;
import com.financeiro.financeiroapi.model.GruposFinanceiros;
import com.financeiro.financeiroapi.model.GruposFinanceiros_;

public class GruposFinanceirosRepositoryImpl implements GruposFinanceirosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<GruposFinanceiros> filtrar(GruposFinanceirosFilter gruposFinanceirosFilter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<GruposFinanceiros> criteria = builder.createQuery(GruposFinanceiros.class);
	    Root<GruposFinanceiros> root = criteria.from(GruposFinanceiros.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(gruposFinanceirosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<GruposFinanceiros> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(gruposFinanceirosFilter));
	}
	
	private Predicate[] criarRestricoes(
			GruposFinanceirosFilter gruposFinanceirosFilter, CriteriaBuilder builder, Root<GruposFinanceiros> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(gruposFinanceirosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(GruposFinanceiros_.ID), gruposFinanceirosFilter.getId()));
		}
		
		// RECEITA-DESPESA
		if(gruposFinanceirosFilter.getReceitaDespesa() != null) {
			String receitaDespesa = gruposFinanceirosFilter.getReceitaDespesa().toUpperCase();
			String letra = receitaDespesa.substring(0, 1);
			if (letra.equals ("R")) {
				predicates.add(builder.equal(root.get(GruposFinanceiros_.RECEITA_DESPESA), 0));
			} else {
				if (letra.equals ("D")) {
					predicates.add(builder.equal(root.get(GruposFinanceiros_.RECEITA_DESPESA), 1));
				}
			}
		}
				
		// DESCRICAO
	    if (StringUtils.hasLength(gruposFinanceirosFilter.getDescricao())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(GruposFinanceiros_.DESCRICAO)),
	              "%" + gruposFinanceirosFilter.getDescricao().toLowerCase() + "%"));
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
	
	private Long total(GruposFinanceirosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<GruposFinanceiros> root = criteria.from(GruposFinanceiros.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
