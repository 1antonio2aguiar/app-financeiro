package com.financeiro.financeiroapi.repository.enderecos;

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

import com.financeiro.financeiroapi.filter.EnderecosFilter;
import com.financeiro.financeiroapi.model.Enderecos;
import com.financeiro.financeiroapi.model.Bairros_;
import com.financeiro.financeiroapi.model.Ceps_;
import com.financeiro.financeiroapi.model.Enderecos_;
import com.financeiro.financeiroapi.model.Logradouros_;

public class EnderecosRepositoryImpl implements EnderecosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Enderecos> filtrar(EnderecosFilter enderecosFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Enderecos> criteria = builder.createQuery(Enderecos.class);
	    Root<Enderecos> root = criteria.from(Enderecos.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(enderecosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Enderecos> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(enderecosFilter));
		
	}
	
	private Predicate[] criarRestricoes(
			EnderecosFilter enderecosFilter, CriteriaBuilder builder, Root<Enderecos> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(enderecosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Enderecos_.ID), enderecosFilter.getId()));
		}
		
		// CEP
		if(enderecosFilter.getCepsFilter().getCep() != null) {
			predicates.add(builder.equal(root.get(Enderecos_.CEPS).get(Ceps_.CEP), enderecosFilter.getCepsFilter().getCep()));
		}
		
		// NOME BAIRRO
	    if (StringUtils.hasLength(enderecosFilter.getBairrosFilter().getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(Enderecos_.BAIRROS).get(Bairros_.NOME)),
	              "%" + enderecosFilter.getBairrosFilter().getNome().toLowerCase() + "%"));
	    }
	    
	    // NOME LOGRADOURO
	    if (StringUtils.hasLength(
            enderecosFilter.getLogradourosFilter().getNome())) {
            predicates.add(
              builder.like(
                  builder.lower(
                      root.get(Enderecos_.LOGRADOUROS).get(Logradouros_.NOME)),
                  "%"
                      + enderecosFilter.getLogradourosFilter().getNome().toLowerCase() + "%"));
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
	
	private Long total(EnderecosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Enderecos> root = criteria.from(Enderecos.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
