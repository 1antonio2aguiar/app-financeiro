package com.financeiro.financeiroapi.repository.TiposGruposFinanceiros;

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

import com.financeiro.financeiroapi.filter.TiposGruposFinanceirosFilter;
import com.financeiro.financeiroapi.model.GruposFinanceiros_;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros_;

public class TiposGruposFinanceirosRepositoryImpl implements TiposGruposFinanceirosRepositoryQuery{
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<TiposGruposFinanceiros> filtrar(TiposGruposFinanceirosFilter tiposGruposFinanceiros, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<TiposGruposFinanceiros> criteria = builder.createQuery(TiposGruposFinanceiros.class);
	    Root<TiposGruposFinanceiros> root = criteria.from(TiposGruposFinanceiros.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(tiposGruposFinanceiros, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<TiposGruposFinanceiros> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(tiposGruposFinanceiros));
	}
	
	private Predicate[] criarRestricoes(
			TiposGruposFinanceirosFilter tiposGruposFinanceiros, CriteriaBuilder builder, Root<TiposGruposFinanceiros> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(tiposGruposFinanceiros.getId() != null) {
			predicates.add(builder.equal(root.get(TiposGruposFinanceiros_.ID), tiposGruposFinanceiros.getId()));
		}
		
		// Recita / Despesa
		if (StringUtils.hasLength(tiposGruposFinanceiros.getGruposFinanceirosFilter().getReceitaDespesa())) {
			String receitaDespesa = tiposGruposFinanceiros.getGruposFinanceirosFilter().getReceitaDespesa().toUpperCase();
			
			System.err.println(receitaDespesa);
			
			String letra = receitaDespesa.substring(0, 1);
			if (letra.equals ("R")) {
				predicates.add(builder.equal(root.get(TiposGruposFinanceiros_.GRUPOS_FINANCEIROS).get(GruposFinanceiros_.RECEITA_DESPESA), 0));
			} else {
			if (letra.equals ("D")) {
				predicates.add(builder.equal(root.get(TiposGruposFinanceiros_.GRUPOS_FINANCEIROS).get(GruposFinanceiros_.RECEITA_DESPESA), 1));
			}
			}
	    }
		
	    // Grupos Financeiros Descricao
	    if (StringUtils.hasLength(tiposGruposFinanceiros.getGruposFinanceirosFilter().getDescricao())) {
		      predicates.add(
		          builder.like(
		              builder.lower(root.get(TiposGruposFinanceiros_.GRUPOS_FINANCEIROS).get(GruposFinanceiros_.DESCRICAO)),
		              "%" + tiposGruposFinanceiros.getGruposFinanceirosFilter().getDescricao().toLowerCase() + "%"));
	    }
	    
	    // Tipos Grupos Financeiros Descrição
	    if (StringUtils.hasLength(tiposGruposFinanceiros.getDescricao())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(TiposGruposFinanceiros_.DESCRICAO)),
	              "%" + tiposGruposFinanceiros.getDescricao().toLowerCase() + "%"));
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
	
	private Long total(TiposGruposFinanceirosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<TiposGruposFinanceiros> root = criteria.from(TiposGruposFinanceiros.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
